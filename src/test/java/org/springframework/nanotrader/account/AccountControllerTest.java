package org.springframework.nanotrader.account;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@WebIntegrationTest(value = "server.port=9876")
@DatabaseSetup("testData.xml")
public class AccountControllerTest {

	@Autowired
	AccountController accountController;

	@Autowired
	AccountProfileController accountProfileController;

	@Test
	public void testFindAccount() {
		assertNotNull(accountController.findAccount(new Long(2)));
		assertNull(accountController.findAccount(new Long(3453)));
	}

	@Test
	public void testSaveAccount() {
		Account a = new Account();
		AccountProfile profile = accountProfileController
				.findAccountProfile(new Long(1));
		assertNotNull(profile);
		a.setAccountProfile(profile);
		a = accountController.saveAccount(a);
		assertNotNull(a);
		Long id = a.getId();
		assertNotNull(id);
		assertNotNull(accountController.findAccount(id));
	}

	@Test
	public void testDeleteAccount() {
		Account a = new Account();
		AccountProfile profile = accountProfileController
				.findAccountProfile(new Long(1));
		assertNotNull(profile);
		a.setAccountProfile(profile);
		a = accountController.saveAccount(a);
		assertNotNull(a);
		Long id = a.getId();
		assertNotNull(id);
		assertNotNull(accountController.findAccount(id));

		accountController.deleteAccount(a);
		assertNull(accountController.findAccount(id));
	}
}

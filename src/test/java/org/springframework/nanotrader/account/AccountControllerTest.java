package org.springframework.nanotrader.account;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebIntegrationTest(value = "server.port=9876")
@DatabaseSetup("testData.xml")
public class AccountControllerTest {

    @Autowired
    AccountController accountController;

    @Test
    public void testFindAccount() {
        assertNotNull(accountController.findAccount(2L));
        assertNull(accountController.findAccount(3453L));
    }

    @Test
    public void testFindAccountByProfileId() {
        List<Account> l = accountController.findByAccountProfileId(1L);
        assertNotNull(l);
        assertTrue(l.size() == 1);
        assertEquals(new Long(1), l.get(0).getAccountProfile().getaccountProfileId());
        assertNull(accountController.findAccount(3453L));
    }
}

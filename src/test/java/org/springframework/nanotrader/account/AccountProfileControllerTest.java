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
public class AccountProfileControllerTest {

    @Autowired
    AccountProfileController accountProfileController;

    @Test
    public void testDeleteAccountProfile() {
        AccountProfile a = new AccountProfile();
        a.setUserId("deleteMe");
        a = accountProfileController.saveAccountProfile(a);
        assertNotNull(a);
        Long id = a.getaccountProfileId();
        assertNotNull(id);
        assertNotNull(accountProfileController.findAccountProfile(id));

        accountProfileController.deleteAccountProfile(a);
        assertNull(accountProfileController.findAccountProfile(id));
    }

    @Test
    public void testFindAccountProfile() {
        assertNotNull(accountProfileController.findAccountProfile(1L));
        assertNull(accountProfileController.findAccountProfile(56665L));
    }

    @Test
    public void testSaveAccountProfile() {
        AccountProfile ap = new AccountProfile();
        ap.setUserId("testing");
        assertNotNull(accountProfileController.saveAccountProfile(ap));
    }

    @Test
    public void testFindByUserid() {
        assertEquals(1, accountProfileController.search("baker", null, null)
                .size());
        assertEquals(0, accountProfileController.search("foo", null, null)
                .size());
    }

    @Test
    public void testFindByUseridAndPassword() {
        assertEquals(1,
                accountProfileController.search("baker", "nbvhvmgh", null)
                        .size());
        assertEquals(0, accountProfileController.search("baker", "foo", null)
                .size());
    }

    @Test
    public void testFindByAuthToken() {
        assertEquals(1, accountProfileController.search(null, null, "aToken")
                .size());
        assertEquals(0, accountProfileController.search(null, null, "foo")
                .size());
    }

    @Test
    public void testGetAccounts() {
        List<Account> l = accountProfileController.getAccounts(1L);
        assertNotNull(l);
        assertEquals(1, l.size());
    }
}

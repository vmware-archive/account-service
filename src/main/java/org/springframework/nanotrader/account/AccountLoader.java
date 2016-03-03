package org.springframework.nanotrader.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AccountLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AccountProfileController accountProfileController;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        AccountProfile profile = new AccountProfile();
        profile.setAddress("123 Test St. Testing, Texas 10203");
        profile.setCreditcard("1234567890");
        profile.setEmail("test@test.com");
        profile.setFullName("Chester Tester");
        profile.setPasswd("test");
        profile.setUserId("test");
        profile.setAuthToken("token");

        Account account1 = new Account();
        account1.setCreationdate(new Date());
        account1.setOpenbalance(100000f);
        account1.setBalance(100000f);
        account1.setLastLogin(new Date());
        account1.setAccountProfile(profile);

        profile.addAccount(account1);

        accountProfileController.saveAccountProfile(profile);
    }
}

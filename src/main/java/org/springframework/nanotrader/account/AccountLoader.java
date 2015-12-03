package org.springframework.nanotrader.account;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AccountLoader implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountProfileRepository accountProfileRepository;

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

		profile = accountProfileRepository.save(profile);

		Account account1 = new Account();
		account1.setCreationdate(new Date());
		account1.setOpenbalance(100000f);
		account1.setAccountProfile(profile);

		account1 = accountRepository.save(account1);
	}
}

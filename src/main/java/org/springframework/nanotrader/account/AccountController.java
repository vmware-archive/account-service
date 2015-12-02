/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.nanotrader.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController implements AccountService, AccountProfileService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountProfileRepository accountProfileRepository;

	@Override
	@RequestMapping(value = "/profiles", method = RequestMethod.DELETE)
	public void deleteAccountProfile(@RequestBody AccountProfile accountProfile) {
		accountProfileRepository.delete(accountProfile);
	}

	@Override
	@RequestMapping(value = "/profiles/{id}", method = RequestMethod.GET)
	public AccountProfile findAccountProfile(@PathVariable Long id) {
		return accountProfileRepository.findOne(id);
	}

	@Override
	@RequestMapping(value = "/profiles", method = RequestMethod.POST)
	public AccountProfile saveAccountProfile(
			@RequestBody AccountProfile accountProfile) {
		return accountProfileRepository.save(accountProfile);
	}

	@Override
	@RequestMapping(value = "/profiles?userId={userId}", method = RequestMethod.GET)
	public AccountProfile findByUserid(String userId) {
		return accountProfileRepository.findByUserId(userId);
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public void deleteAccount(@RequestBody Account account) {
		accountRepository.delete(account);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Account findAccount(@PathVariable Long id) {
		return accountRepository.findOne(id);
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Account saveAccount(@RequestBody Account account) {
		return accountRepository.save(account);
	}

	@Override
	public AccountProfile findByUseridAndPasswd(String userId, String passwd) {
		return accountProfileRepository.findByUserIdAndPasswd(userId, passwd);
	}

	@Override
	public AccountProfile findByAuthtoken(String authToken) {
		return accountProfileRepository.findByAuthToken(authToken);
	}

	@Override
	public Account findByProfile(AccountProfile accountProfile) {
		return accountRepository.findByAccountProfile(accountProfile);
	}
}

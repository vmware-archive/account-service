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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class AccountProfileController {

	@Autowired
	AccountProfileRepository accountProfileRepository;

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public void deleteAccountProfile(@RequestBody AccountProfile accountProfile) {
		accountProfileRepository.delete(accountProfile);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<AccountProfile> search(
			@RequestParam(value = "userId", required = false) String id,
			@RequestParam(value = "passwd", required = false) String passwd,
			@RequestParam(value = "authToken", required = false) String authToken) {

		if (id != null && passwd != null) {
			List<AccountProfile> l = new ArrayList<AccountProfile>();
			AccountProfile ap = accountProfileRepository.findByUserIdAndPasswd(
					id, passwd);
			if (ap != null) {
				l.add(ap);
			}
			return l;
		}

		if (id != null) {
			List<AccountProfile> l = new ArrayList<AccountProfile>();
			AccountProfile ap = accountProfileRepository.findByUserId(id);
			if (ap != null) {
				l.add(ap);
			}
			return l;
		}

		if (authToken != null) {
			List<AccountProfile> l = new ArrayList<AccountProfile>();
			AccountProfile ap = accountProfileRepository
					.findByAuthToken(authToken);
			if (ap != null) {
				l.add(ap);
			}
			return l;
		}

		return accountProfileRepository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AccountProfile findAccountProfile(@PathVariable Long id) {
		return accountProfileRepository.findOne(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public AccountProfile saveAccountProfile(
			@RequestBody AccountProfile accountProfile) {
		return accountProfileRepository.save(accountProfile);
	}
}
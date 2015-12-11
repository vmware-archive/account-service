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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "AccountProfile")
public class AccountProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id = new Long(-1);

	public Long getId() {
		return this.id;
	}

	@JsonManagedReference
	@OneToMany(mappedBy = "accountProfile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Account> accounts;

	private String address;

	private String passwd;

	@Column(unique = true)
	@NotNull
	private String userId;

	private String email;

	private String creditCard;

	private String fullName;

	private String authToken;

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String s) {
		this.authToken = s;
	}

	public List<Account> getAccounts() {
		if (accounts == null) {
			accounts = new ArrayList<Account>();
		}
		return accounts;
	}

	public void addAccount(Account a) {
		if (a == null) {
			return;
		}
		a.setAccountProfile(this);
		getAccounts().add(a);
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String s) {
		this.address = s;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String s) {
		this.passwd = s;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String s) {
		this.userId = s;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String s) {
		this.email = s;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditcard(String s) {
		this.creditCard = s;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String s) {
		this.fullName = s;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public int hashCode() {
		return getId().intValue();
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof AccountProfile)) {
			return false;
		}
		return o.hashCode() == hashCode();
	}
}

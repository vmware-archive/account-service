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
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountProfileId")
	@JsonBackReference
	@NotNull
	private AccountProfile accountProfile;

	private Date creationDate = new Date();

	private float openBalance;

	private int logoutCount;

	private float balance;

	private Date lastLogin;

	private int loginCount;

	public AccountProfile getAccountProfile() {
		return accountProfile;
	}

	public void setAccountProfile(AccountProfile p) {
		this.accountProfile = p;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationdate(Date d) {
		this.creationDate = d;
	}

	public float getOpenBalance() {
		return openBalance;
	}

	public void setOpenbalance(float f) {
		this.openBalance = f;
	}

	public int getLogoutCount() {
		return logoutCount;
	}

	public void setLogoutcount(int i) {
		this.logoutCount = i;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float f) {
		this.balance = f;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date d) {
		this.lastLogin = d;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int i) {
		this.loginCount = i;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id = new Long(-1);

	public Long getId() {
		return this.id;
	}

	public void setId(Long l) {
		if (l != null) {
			this.id = l;
		}
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

		if (!(o instanceof Account)) {
			return false;
		}
		return o.hashCode() == hashCode();
	}

}

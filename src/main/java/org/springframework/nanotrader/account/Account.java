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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Account")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountId", scope = Account.class)
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountProfileId")
    @NotNull
    private AccountProfile accountProfile;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date creationDate = new Date();

    private float openBalance;

    private int logoutCount;

    private float balance;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
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
    private Long accountId = new Long(-1);

    public Long getAccountId() {
        return this.accountId;
    }

    public void setId(Long l) {
        if (l != null) {
            this.accountId = l;
        }
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public int hashCode() {
        return getAccountId().intValue();
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

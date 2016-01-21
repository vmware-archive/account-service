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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AccountProfile")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountProfileId", scope = AccountProfile.class)
public class AccountProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountProfileId = new Long(-1);

    public Long getaccountProfileId() {
        return this.accountProfileId;
    }

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
        return getaccountProfileId().intValue();
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

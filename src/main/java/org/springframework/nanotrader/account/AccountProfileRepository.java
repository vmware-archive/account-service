package org.springframework.nanotrader.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountProfileRepository extends
		JpaSpecificationExecutor<AccountProfile>,
		JpaRepository<AccountProfile, Long> {

	public AccountProfile findByUserId(String userId);

	public AccountProfile findByAuthToken(String authToken);

}

package org.springframework.nanotrader.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaSpecificationExecutor<Account>,
		JpaRepository<Account, Long> {

	public Account findByAccountProfile(AccountProfile ap);

}

package com.smartbank.repository;


import org.springframework.stereotype.Repository;

import com.smartbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	

}

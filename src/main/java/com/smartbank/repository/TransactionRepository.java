package com.smartbank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartbank.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>{
	List<Transaction> findByAccountAccountIdOrderByTransactionDateDesc(Long accountId);
	List<Transaction> findTop10ByAccountAccountIdOrderByTransactionDateDesc(
	        Long accountId
	);
}

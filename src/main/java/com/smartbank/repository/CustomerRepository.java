package com.smartbank.repository;

import org.springframework.stereotype.Repository;
import com.smartbank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{
	
	
	
}

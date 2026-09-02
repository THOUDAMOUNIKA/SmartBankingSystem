package com.smartbank.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long accountId;
	
	@Column(name="account_number",nullable=false,unique=true)
	private String accountNumber;
	
	@Column(nullable=false,precision =15,scale=2)
	private String accountType;
	
	@Column(nullable=false)
	private BigDecimal balance;
	
	@Column(nullable=false)
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable=false)
	private Customer customer;

}

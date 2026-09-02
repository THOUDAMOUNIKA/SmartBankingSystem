package com.smartbank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name="transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	
	@ManyToOne
	@JoinColumn(name="account_id",nullable=false)
	private Account account;
	
	@Column(nullable=false)
	private String transactionType;
	
	@Column(nullable=false,precision=15,scale=2)
	private BigDecimal amount;
	
	
	@Column(nullable=false)
	private LocalDateTime transactionDate;
	
	@Column(length=500)
	private String description;
	
	@Column(nullable=false)
	private String referenceNumber;
	

}

package com.smartbank.mapper;

import com.smartbank.dto.response.TransactionResponseDTO;
import com.smartbank.entity.Transaction;

public class TransactionMapper {
	
	public static TransactionResponseDTO  toResponseDTO(Transaction transaction)
	{
		
		
		return TransactionResponseDTO.builder()
				.transactionId(transaction.getTransactionId())
				.accountId(transaction.getAccount().getAccountId())
				.transactionType(transaction.getTransactionType())
				.amount(transaction.getAmount())
				.transactionDate(transaction.getTransactionDate())
				.description(transaction.getDescription())
				.referenceNumber(transaction.getReferenceNumber())
				.balance(transaction.getAccount().getBalance())
				.build();
		
	}

}

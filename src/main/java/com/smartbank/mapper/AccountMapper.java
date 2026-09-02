package com.smartbank.mapper;

import com.smartbank.dto.request.AccountCreateRequestDTO;
import com.smartbank.dto.response.AccountResponseDTO;
import com.smartbank.entity.Account;

public class AccountMapper {
	
	
	public static Account toEntity(AccountCreateRequestDTO request)
	{
		return Account.builder()
				.accountType(request.getAccountType())
				.balance(request.getInitialDeposit())
				.status("ACTIVE")
				.build();
	}
	
	public static AccountResponseDTO toResponseDTO(Account account) {
		
		
		return AccountResponseDTO.builder()
				.accountId(account.getAccountId())
				.accountNumber(account.getAccountNumber())
				.accountType(account.getAccountType())
				.balance(account.getBalance())
				.status(account.getStatus())
				.customerId(account.getCustomer().getCustomerId())
				.build();
		
	}

}

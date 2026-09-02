package com.smartbank.dto.response;

import java.math.BigDecimal;

import com.smartbank.entity.Account.AccountBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {
	private Long accountId;
	private String accountNumber;
	private String accountType;
	private BigDecimal balance;
	private String status;
	private Long customerId;


}

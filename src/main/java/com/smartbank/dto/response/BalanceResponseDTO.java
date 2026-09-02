package com.smartbank.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponseDTO {
	
	private Long accountId;
	private String accountNumber;
	
	private BigDecimal balance;
	
	private String status;
	
}

package com.smartbank.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateRequestDTO {
	@NotNull(message="Account Id is required")
	private Long accountId;
	
	@NotNull(message="Deposit amount is required")
	@Positive(message="Deposit amount must be greater than zero")
	private BigDecimal amount;
	private String description;

}

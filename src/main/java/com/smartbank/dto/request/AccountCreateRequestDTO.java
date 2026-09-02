package com.smartbank.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequestDTO {
	@NotBlank(message="Account type is required")
	@Pattern(
	    regexp = "SAVINGS|CURRENT|SALARY|FIXED DEPOSIT",
	    message = "Account type must be SAVINGS or CURRENT or SALARY or FIXED DEPOSIT"
	)
	private String accountType;
	
	@NotNull(message="Initial  deposit is required")
	@PositiveOrZero(message="Initial deposit cannot be negative")
	private BigDecimal initialDeposit;
	
	@NotNull(message="Customer ID is required")
	private Long customerId;
	

}

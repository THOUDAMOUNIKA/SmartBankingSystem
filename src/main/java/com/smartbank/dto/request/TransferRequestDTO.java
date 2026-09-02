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
public class TransferRequestDTO {
	
	@NotNull(message = "Sender account ID is required")
	private Long senderAccountId;
	
	@NotNull(message="Receiver account ID is required")
	private Long receiverAccountId;
	
	@NotNull(message="Transfer amount is required")
	@Positive(message="Transfer amount must be greater than zero")
	private BigDecimal amount;
	private String description;
	
	

}

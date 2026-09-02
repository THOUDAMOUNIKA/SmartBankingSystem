package com.smartbank.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
	private Long transactionId;
	private Long accountId;
	private String transactionType;
	private BigDecimal amount;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime transactionDate;
	private String description;
	private String referenceNumber;
	private BigDecimal balance;

}

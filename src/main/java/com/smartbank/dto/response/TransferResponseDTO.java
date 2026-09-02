package com.smartbank.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDTO {
	
	private Long senderAccountId;
	private Long receiverAccountId;
	
	private BigDecimal amount;
	
	private BigDecimal senderBalance;
	
	private BigDecimal receiverBalance;
	
	private LocalDateTime transactionDate;
	
	private String referenceNumber;

}

package com.smartbank.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequestDTO {
	
	@NotBlank(message="Account type is required")
	private String accountType;
	private String status;

}

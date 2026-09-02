package com.smartbank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
	
	private Long customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

}

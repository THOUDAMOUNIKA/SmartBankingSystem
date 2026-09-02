package com.smartbank.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequestDTO {
	@NotBlank(message = "First name is required")
	@Size(min = 2, max = 50,
	      message = "First name must be between 2 and 50 characters")
	private String firstName;
	
	@NotBlank(message="Last name is required")
	@Size(min=2,max=50, message="Last name must be between 2 and 50 characters")
	private String lastName;
	
	@NotBlank(message="Email is required")
	@Email(message="Please provide a valid email address")
	private String email;
	
	@NotBlank(message="Phone number is required")
	@Pattern(regexp="^[6-9][0-9]{9}$",
	message="Please provide a valid 10-digit phone number")
	private String phoneNumber;

}

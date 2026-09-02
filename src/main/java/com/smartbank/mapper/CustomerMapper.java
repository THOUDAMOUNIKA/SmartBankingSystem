package com.smartbank.mapper;

import org.springframework.stereotype.Component;

import com.smartbank.dto.request.CustomerCreateRequestDTO;
import com.smartbank.dto.request.CustomerUpdateRequestDTO;
import com.smartbank.dto.response.CustomerResponseDTO;
import com.smartbank.entity.Customer;


@Component
public class CustomerMapper {
	public Customer toEntity(CustomerCreateRequestDTO request)
	{
		 return new Customer(
	                request.getFirstName(),
	                request.getLastName(),
	                request.getEmail(),
	                request.getPhoneNumber()
	        );
				
	}
	
	public CustomerResponseDTO toResponseDTO(Customer customer)
	{
		return CustomerResponseDTO.builder()
				.customerId(customer.getCustomerId())
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.email(customer.getEmail())
				.phoneNumber(customer.getPhoneNumber())
				.build();
	}
	
	public void updateEntity(Customer customer, CustomerUpdateRequestDTO request)
	{
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setPhoneNumber(request.getPhoneNumber());
	}
}

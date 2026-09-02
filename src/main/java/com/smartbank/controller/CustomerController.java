package com.smartbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.dto.request.CustomerCreateRequestDTO;
import com.smartbank.dto.request.CustomerUpdateRequestDTO;
import com.smartbank.dto.response.CustomerResponseDTO;
import com.smartbank.service.CustomerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
		
	}
	
	@PostMapping
	public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerCreateRequestDTO request)
	{
		CustomerResponseDTO response = customerService.createCustomer(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable(name="customerId") Long customerId)
	{
		CustomerResponseDTO response = customerService.getCustomerById(customerId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers()
	{
		List<CustomerResponseDTO> responses = customerService.getAllCustomers();
		return ResponseEntity.ok(responses);
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable(name="customerId") Long customerId,@Valid @RequestBody CustomerUpdateRequestDTO request)
	{
		CustomerResponseDTO response = customerService.updateCustomer(customerId, request);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable(name="customerId") Long CustomerId)
	{
		customerService.deleteCustomer(CustomerId);
		return ResponseEntity.noContent().build();
	}
	
}

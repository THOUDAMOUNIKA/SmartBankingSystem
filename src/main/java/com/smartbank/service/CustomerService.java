package com.smartbank.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smartbank.dto.request.CustomerCreateRequestDTO;
import com.smartbank.dto.request.CustomerUpdateRequestDTO;
import com.smartbank.dto.response.CustomerResponseDTO;
import com.smartbank.entity.Customer;
import com.smartbank.exception.CustomerNotFoundException;
import com.smartbank.mapper.CustomerMapper;
import com.smartbank.repository.CustomerRepository;

@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	
	public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper)
	{
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	public CustomerResponseDTO createCustomer(CustomerCreateRequestDTO request)
	{
		logger.info("Creating customer with email: {}", request.getEmail());
		Customer customer = customerMapper.toEntity(request);
		Customer savedCustomer = customerRepository.save(customer);
		logger.info("Creating customer with email: {}",request.getEmail());
		return customerMapper.toResponseDTO(savedCustomer);
	}
	
	public CustomerResponseDTO getCustomerById(Long customerId)
	{
		// here we perform operations on Entity, and later convert entity to DTO's
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> 
				new CustomerNotFoundException(
						"Customer not found with id: "+customerId));	
		// ton handle missing customers explicitly
		
		logger.info("Fetching customer with id: {}",customerId);
		return customerMapper.toResponseDTO(customer);
	}
	
	public List<CustomerResponseDTO> getAllCustomers()
	{
		List<Customer> customers = customerRepository.findAll();
		return customers.stream().map(customerMapper::toResponseDTO).toList();
	}
	
	public CustomerResponseDTO updateCustomer(Long customerId, CustomerUpdateRequestDTO request)
	{
		
		logger.info("Updating customer with id:{}",customerId);
		// Here finding the customer with particular ID
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()-> new CustomerNotFoundException("Customer not found with Id: customerId"));
		//here customer gets updated
		customerMapper.updateEntity(customer, request);
		
		// After update we are saving the cutomer
		Customer updatedCustomer = customerRepository.save(customer);
		
		//now convert customer entity to DTO
		return customerMapper.toResponseDTO(updatedCustomer);
	}
	
	public void deleteCustomer(Long customerId)
	{
		logger.info("Deleting customer with id:{}",customerId);
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> 
				new CustomerNotFoundException("Customer not found with ID: "+customerId));
		
		//Here as we are deleting no need of conversion
		customerRepository.delete(customer);
	}
	
	

}

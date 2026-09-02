package com.smartbank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartbank.dto.request.AccountCreateRequestDTO;
import com.smartbank.dto.request.AccountUpdateRequestDTO;
import com.smartbank.dto.response.AccountResponseDTO;
import com.smartbank.dto.response.BalanceResponseDTO;
import com.smartbank.entity.Account;
import com.smartbank.entity.Customer;
import com.smartbank.exception.AccountNotFoundException;
import com.smartbank.mapper.AccountMapper;
import com.smartbank.repository.AccountRepository;
import com.smartbank.repository.CustomerRepository;


@Service
public class AccountService {
	
	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;
	
	public AccountService(AccountRepository accountRepository, 
			CustomerRepository customerRepository)
	{
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}
	
	
	public AccountResponseDTO createAccount(AccountCreateRequestDTO request)
	{
		Customer customer = customerRepository.findById(request.getCustomerId())
				.orElseThrow(() -> new RuntimeException(
						"Customer not found with id: "+request.getCustomerId()));
		
	
	Account account = AccountMapper.toEntity(request);
	account.setAccountNumber(generateAccountNumber());
	account.setCustomer(customer);
	Account savedAccount = accountRepository.save(account);
	
	return AccountMapper.toResponseDTO(savedAccount);
	}
	
	private String generateAccountNumber()
	{
		return "SB"+ UUID.randomUUID()
						.toString()
						.replace("-","")
						.substring(0,10)
						.toUpperCase();
	}
	
	public AccountResponseDTO getAccountById(Long accountId)
	{
		Account account = accountRepository
				.findById(accountId)
				.orElseThrow(()-> new RuntimeException(
						"Account not found with id "+accountId));
		
		return AccountMapper.toResponseDTO(account);
	}
	
	public List<AccountResponseDTO> getAllAccounts()
	{
		return accountRepository.findAll().stream()
				.map(AccountMapper::toResponseDTO).toList();
	}
	
	@Transactional
	public AccountResponseDTO updateAccount(Long accountId, AccountUpdateRequestDTO request)
	{
		Account account = accountRepository.findById(accountId).orElseThrow(
			() -> new RuntimeException("Account not found with id: "+accountId));
		
		account.setAccountType(request.getAccountType());
		account.setStatus(request.getStatus());
		
		Account updateAccount = accountRepository.save(account);
		return AccountMapper.toResponseDTO(updateAccount);
	}
	
	@Transactional
	public AccountResponseDTO closeAccount(Long accountId)
	{
		Account account = accountRepository.findById(accountId).orElseThrow(
				()-> new RuntimeException("Account not found by id :"+accountId));
		
		if(account.getBalance().compareTo(BigDecimal.ZERO) > 0)
		{
			throw new RuntimeException("Account cannot be closed while balance is greater than zero");
		}
		
		if("CLOSED".equals(account.getStatus()))
		{
			throw new RuntimeException("Account is already closed");
		}
		
		account.setStatus("CLOSED");
		Account updatedAccount = accountRepository.save(account);
		return AccountMapper.toResponseDTO(updatedAccount);
		
	}
	
	@Transactional(readOnly = true)
	public BalanceResponseDTO getBlance(Long accountId)
	{
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException("Account not found with id: "+ accountId));
		
		return BalanceResponseDTO.builder().accountId(account.getAccountId())
				.accountNumber(account.getAccountNumber())
				.balance(account.getBalance())
				.status(account.getStatus())
				.build();
		
	}
	

}

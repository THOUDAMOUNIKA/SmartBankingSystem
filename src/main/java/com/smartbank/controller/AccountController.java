package com.smartbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.dto.request.AccountCreateRequestDTO;
import com.smartbank.dto.request.AccountUpdateRequestDTO;
import com.smartbank.dto.response.AccountResponseDTO;
import com.smartbank.dto.response.BalanceResponseDTO;
import com.smartbank.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	private final AccountService accountService;
	
	public AccountController(AccountService accountService)
	{
		this.accountService = accountService;
	}
	
	@PostMapping
	public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountCreateRequestDTO request)
	{
		AccountResponseDTO response = accountService.createAccount(request);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(response);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable(name="accountId") Long accountId)
	{
		AccountResponseDTO response = accountService.getAccountById(accountId);
		return ResponseEntity.ok(response);	
		
	}
	
	@GetMapping
	public ResponseEntity<List<AccountResponseDTO>> getAllAccounts()
	{
		List<AccountResponseDTO> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	@PutMapping("/{accountId}")
	public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable(name="accountId") Long accountId, @Valid @RequestBody AccountUpdateRequestDTO request)
	{
		AccountResponseDTO response = accountService.updateAccount(accountId, request);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{accountId}/close")
	public ResponseEntity<AccountResponseDTO> closeAccount(@PathVariable(name="accountId") Long accountId)
	{
		AccountResponseDTO  response = accountService.closeAccount(accountId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{accountId}/balance")
	public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable(name="accountId") Long accountId)
	{
		BalanceResponseDTO response = accountService.getBlance(accountId);
		return ResponseEntity.ok(response);
	}

}

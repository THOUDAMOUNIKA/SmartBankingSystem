package com.smartbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.dto.request.TransactionCreateRequestDTO;
import com.smartbank.dto.request.TransferRequestDTO;
import com.smartbank.dto.response.TransactionResponseDTO;
import com.smartbank.dto.response.TransferResponseDTO;
import com.smartbank.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	private final TransactionService transactionService;
	public TransactionController(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<TransactionResponseDTO> deposit(@Valid @RequestBody TransactionCreateRequestDTO request)
	{
		TransactionResponseDTO response = transactionService.deposit(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<TransactionResponseDTO> withdraw(@RequestBody TransactionCreateRequestDTO request)
	{
		TransactionResponseDTO response = transactionService.withdraw(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<TransferResponseDTO> transfer(@Valid @RequestBody TransferRequestDTO request)
	{
		TransferResponseDTO response = transactionService.transfer(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@GetMapping("/account/{accountId}")
	public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByAccount(@PathVariable(name="accountId") Long accountId)
	{
		List<TransactionResponseDTO> transactions = transactionService.getTransactionsByAccount(accountId);
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/account/{accountId}/mini-statement")
	public ResponseEntity<List<TransactionResponseDTO>> getMiniStatement(
	        @PathVariable(name="accountId") Long accountId) {

	    List<TransactionResponseDTO> transactions =
	            transactionService.getMiniStatement(accountId);

	    return ResponseEntity.ok(transactions);
	}
	


}

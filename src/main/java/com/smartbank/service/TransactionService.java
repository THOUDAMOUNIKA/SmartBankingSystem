package com.smartbank.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smartbank.dto.request.TransactionCreateRequestDTO;
import com.smartbank.dto.request.TransferRequestDTO;
import com.smartbank.dto.response.TransactionResponseDTO;
import com.smartbank.dto.response.TransferResponseDTO;
import com.smartbank.entity.Account;
import com.smartbank.entity.Transaction;
import com.smartbank.exception.AccountNotFoundException;
import com.smartbank.exception.InsufficientBalanceException;
import com.smartbank.exception.InvalidTransactionException;
import com.smartbank.mapper.TransactionMapper;
import com.smartbank.repository.AccountRepository;
import com.smartbank.repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	 
	
	public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository)
	{
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
	}

	
	@Transactional
	public TransactionResponseDTO deposit(TransactionCreateRequestDTO request)
	{
		Account account = accountRepository.findById(request.getAccountId())
											.orElseThrow(() -> new RuntimeException(
													"Account not found with id:"+request.getAccountId()));
		
		if("CLOSED".equals(account.getStatus()))
		{
			throw new InvalidTransactionException("Cannot deposit into a closed account");
		}
		
		// Handled in the DTO Layer (Input Validation)
		
//		if(request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0)
//				{
//			throw new InvalidTransactionException("Deposit amount must be greater than zero");
//				}
				
		// Update account balance
		account.setBalance(account.getBalance().add(request.getAmount()));
		
		accountRepository.save(account);
		
		
		//Create transaction record
		
		Transaction transaction = Transaction.builder()
									.account(account)
									.transactionType("DEPOSIT")
									.amount(request.getAmount())
									.transactionDate(LocalDateTime.now())
									.description(request.getDescription())
									.referenceNumber(generateReferenceNumber())
									.build();
		
		Transaction savedTransaction = transactionRepository.save(transaction);
		
		return TransactionMapper.toResponseDTO(savedTransaction);
	}
	
	@Transactional
	public TransactionResponseDTO withdraw(TransactionCreateRequestDTO request)
	{
		Account account = accountRepository
							.findById(request.getAccountId())
							.orElseThrow(() -> new RuntimeException(
									"Account not found with id: "
							+ request.getAccountId()));
		
		if("CLOSED".equals(account.getStatus()))
		{
			throw new InvalidTransactionException(
					"Cannot withdraw from a closed account");
		}
		
		if(request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0)
		{
			throw new InvalidTransactionException("Withdrawal amount must be greater than zero");
		}
		
		if(account.getBalance().compareTo(request.getAmount()) < 0)
		{
			throw new InsufficientBalanceException("Insufficient balance in sender account");
		}
		
		account.setBalance(account.getBalance().subtract(request.getAmount()));
		
		accountRepository.save(account);
		
		Transaction transaction = Transaction.builder()
										.account(account)
										.transactionType("WITHDRAWAL")
										.amount(request.getAmount())
										.transactionDate(LocalDateTime.now())
										.description(request.getDescription())
										.referenceNumber(generateReferenceNumber())
										.build();
		Transaction savedTransaction = transactionRepository.save(transaction);
		
		return TransactionMapper.toResponseDTO(savedTransaction);
							
	}
	
	@Transactional
	public TransferResponseDTO transfer(TransferRequestDTO request)
	{
		Account sender = accountRepository.findById(request.getSenderAccountId())
							.orElseThrow(() -> new RuntimeException("Sender account not found with id:"
							 + request.getSenderAccountId()));
		Account receiver = accountRepository.findById(request.getReceiverAccountId())
							.orElseThrow(() -> new RuntimeException("Receiver account not found with id:" 
								+ request.getReceiverAccountId()));
		
		if(sender.getAccountId().equals(receiver.getAccountId()))
		{
			throw new InvalidTransactionException("Sender and receiver accounts cannnot be the same");
		}
		
		if("CLOSED".equals(sender.getStatus()))
		{
			throw new RuntimeException("Cannot transfer from a closed account");
		}
		
		if("CLOSED".equals(receiver.getStatus()))
		{
			throw new InvalidTransactionException("Cannot transfer to a closed account");
		}
		
		// Handled in the DTO Layer (Input Validation)
//		if(request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0)
//		{
//			throw new InvalidTransactionException("Transfer amount must be greater than zero");
//		}
		
		if(sender.getBalance().compareTo(request.getAmount()) < 0)
		{
			throw new InsufficientBalanceException("Insufficient balance in sender amount");
		}
		
		//Deduct from sender
		sender.setBalance(sender.getBalance().subtract(request.getAmount()));
		
		//Add to receiver
		receiver.setBalance(receiver.getBalance().add(request.getAmount()));
		
		accountRepository.save(sender);
		accountRepository.save(receiver);
		
		String transferReference = generateReferenceNumber();
		
		//create sender transaction
		Transaction senderTransaction = Transaction.builder()
											.account(sender)
											.transactionType("TRANSFER")
											.amount(request.getAmount())
											.transactionDate(LocalDateTime.now())
											.description(request.getDescription())
											.referenceNumber(transferReference)
											.build();
		
		//create receiver transaction
		Transaction receiverTransaction = Transaction.builder()
				.account(receiver)
				.transactionType("TRANSFER")
				.amount(request.getAmount())
				.transactionDate(LocalDateTime.now())
				.description(request.getDescription())
				.referenceNumber(transferReference)
				.build();
		
		transactionRepository.save(senderTransaction);
		transactionRepository.save(receiverTransaction);
		
		return TransferResponseDTO.builder()
				.senderAccountId(sender.getAccountId())
				.receiverAccountId(receiver.getAccountId())
				.amount(request.getAmount())
				.senderBalance(sender.getBalance())
				.receiverBalance(receiver.getBalance())
				.transactionDate(LocalDateTime.now())
				.referenceNumber(senderTransaction.getReferenceNumber())
				.build();
											
	}
	
	
	private String generateReferenceNumber()
	{
		return "TXN-"+ UUID.randomUUID()
						.toString()
						.replace("-","")
						.substring(0,12)
						.toUpperCase();
		
	}
	
	
	@Transactional(readOnly = true)
	public List<TransactionResponseDTO> getTransactionsByAccount(Long accountId)
	{
		accountRepository.findById(accountId)
						.orElseThrow(() -> new RuntimeException("Account not found with id: "+accountId));
		
		List<Transaction> transactions = transactionRepository.findByAccountAccountIdOrderByTransactionDateDesc(accountId);
		
		return transactions.stream().map(TransactionMapper::toResponseDTO).toList();
	}
	
	@Transactional(readOnly=true)
	public List<TransactionResponseDTO> getMiniStatement(Long accountId)
	{
		accountRepository.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException("Account not found with id: + accountId"));
		
		List<Transaction> transactions = transactionRepository.findTop10ByAccountAccountIdOrderByTransactionDateDesc(accountId);
		return transactions.stream().map(TransactionMapper::toResponseDTO)
				.toList();
	}
}

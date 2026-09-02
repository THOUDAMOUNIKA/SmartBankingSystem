package com.smartbank.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smartbank.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFound
	(CustomerNotFoundException exception)
	{
		ErrorResponse errorResponse = ErrorResponse.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message(exception.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex)
	{
		ErrorResponse error = ErrorResponse.builder()
		        .status(HttpStatus.BAD_REQUEST.value())
		        .message(ex.getMessage())
		        .build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
	@ExceptionHandler(InvalidTransactionException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTransaction(InvalidTransactionException ex)
	{
		ErrorResponse error = ErrorResponse.builder()
		        .status(HttpStatus.BAD_REQUEST.value())
		        .message(ex.getMessage())
		        .build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex)
	{
		ErrorResponse error = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.value())
				.message(ex.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex)
	{
		String message =  ex.getBindingResult()
							.getFieldErrors()
							.stream()
							.map(error -> error.getField() + ": "+ error.getDefaultMessage())
							.collect(Collectors.joining(","));
		
		ErrorResponse error = ErrorResponse.builder()
								.status(HttpStatus.BAD_REQUEST.value())
								.message(message)
								.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}


package com.moinammaoueni.smartHire.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.moinammaoueni.smartHire.api.dto.erreur.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(JobNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleJobNotFound(JobNotFoundException ex) {

		ErrorResponse error = ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {

		ErrorResponse error = ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.CONFLICT.value())
				.timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerUserNotFound(UserNotFoundException ex){
		ErrorResponse errorResponse = ErrorResponse.builder()
				.message(ex.getMessage())
				.status(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponse);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

	    String message = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(error -> error.getField() + " : " + error.getDefaultMessage())
	            .findFirst()
	            .orElse("Validation error");

	    ErrorResponse error = ErrorResponse.builder()
	            .message(message)
	            .status(HttpStatus.BAD_REQUEST.value())
	            .timestamp(LocalDateTime.now())
	            .build();

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
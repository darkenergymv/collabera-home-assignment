package com.darkenergy.library_service.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.darkenergy.library_service.dto.ApiError;

@RestControllerAdvice
public class RestExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
		log.error("⚡ RestExceptionHandler caught MethodArgumentNotValidException: {}", ex.getMessage());
	    var details = ex.getBindingResult().getFieldErrors()
	        .stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).collect(Collectors.toList());
	    return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed", details));
	  }
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
		log.error("⚡ RestExceptionHandler caught NotFoundException: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
		log.error("⚡ RestExceptionHandler caught BadRequestException: {}", ex.getMessage());
		return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleAny(Exception ex) {
		log.error("⚡ RestExceptionHandler caught Exception: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error"));
	}

}

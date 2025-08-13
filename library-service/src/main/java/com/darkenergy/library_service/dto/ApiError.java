package com.darkenergy.library_service.dto;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ApiError {
	@Getter @Setter 
	private Instant timestamp = Instant.now();
	
	@Getter @Setter 
	private int status;
	
	@Getter @Setter 
	private String message;
	
	@Getter @Setter 
	private List<String> details;
	
	public ApiError() {}
	
	public ApiError(int status, String message) {
	    this.status = status; this.message = message;
    }
	
	public ApiError(int status, String message, List<String> details) {
	    this.status = status; this.message = message; this.details = details;
	  }
}

package com.darkenergy.library_service.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class ApiError {
	private Instant timestamp = Instant.now();

	private int status;

	private String message;

	private List<String> details;

	public ApiError() {	}

	public ApiError(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public ApiError(int status, String message, List<String> details) {
		this.status = status;
		this.message = message;
		this.details = details;
	}
}

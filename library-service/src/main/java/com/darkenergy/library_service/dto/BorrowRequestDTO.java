package com.darkenergy.library_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class BorrowRequestDTO {
	
	@NotNull(message = "bookId is required")
	@Getter @Setter 
	private Long bookId;

	@NotNull(message = "borrowerId is required")
	@Getter @Setter 
	private Long borrowerId;
}

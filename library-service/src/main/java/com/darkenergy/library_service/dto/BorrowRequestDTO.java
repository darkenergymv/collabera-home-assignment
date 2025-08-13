package com.darkenergy.library_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestDTO {
	
	@NotNull(message = "bookId is required")
	private Long bookId;

	@NotNull(message = "borrowerId is required")
	private Long borrowerId;

	public Long getBookId() {
		return bookId;
	}

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}
}

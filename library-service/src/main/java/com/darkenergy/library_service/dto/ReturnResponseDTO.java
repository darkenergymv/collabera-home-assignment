package com.darkenergy.library_service.dto;

import java.time.Instant;

import lombok.Builder;

@Builder
public class ReturnResponseDTO {
	private Long recordId;
	private Long bookId;
	private Long borrowerId;
	private Instant borrowedAt;
	private Instant returnedAt;
	
	public ReturnResponseDTO() {}

	public ReturnResponseDTO(Long recordId, Long bookId, Long borrowerId, Instant borrowedAt, Instant returnedAt) {
		this.recordId = recordId;
		this.bookId = bookId;
		this.borrowerId = borrowerId;
		this.borrowedAt = borrowedAt;
		this.returnedAt = returnedAt;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public Instant getBorrowedAt() {
		return borrowedAt;
	}

	public void setBorrowedAt(Instant borrowedAt) {
		this.borrowedAt = borrowedAt;
	}

	public Instant getReturnedAt() {
		return returnedAt;
	}

	public void setReturnedAt(Instant returnedAt) {
		this.returnedAt = returnedAt;
	}

}

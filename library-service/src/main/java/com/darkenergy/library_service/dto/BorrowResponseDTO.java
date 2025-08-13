package com.darkenergy.library_service.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
public class BorrowResponseDTO {
	private Long recordId;
	
	private Long bookId;
	
	private BookResponseDTO book;
	
	private Long borrowerId;
	
	private BorrowerResponseDTO borrower;
	
	private Instant borrowedAt;
	
	private Instant returnedAt;

	public BorrowResponseDTO() {}

	public BorrowResponseDTO(Long recordId, Long bookId, BookResponseDTO book, Long borrowerId,
			BorrowerResponseDTO borrower, Instant borrowedAt, Instant returnedAt) {
		this.recordId = recordId;
		this.bookId = bookId;
		this.book = book;
		this.borrowerId = borrowerId;
		this.borrower = borrower;
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

	public BookResponseDTO getBook() {
		return book;
	}

	public void setBook(BookResponseDTO book) {
		this.book = book;
	}

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public BorrowerResponseDTO getBorrower() {
		return borrower;
	}

	public void setBorrower(BorrowerResponseDTO borrower) {
		this.borrower = borrower;
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

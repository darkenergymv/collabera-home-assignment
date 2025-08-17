package com.darkenergy.library_service.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "borrow_records", indexes = {
    @Index(name = "idx_borrow_records_book", columnList = "book_id"),
    @Index(name = "idx_borrow_records_borrower", columnList = "borrower_id")
})
@Data
@Builder
public class BorrowRecord {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="borrower_id")
    private Borrower borrower;

    @Column(nullable=false)
    private Instant borrowedAt;

    @Column
    private Instant returnedAt;
    
    public BorrowRecord() {}

	public BorrowRecord(Long id, Book book, Borrower borrower, Instant borrowedAt, Instant returnedAt) {
		this.id = id;
		this.book = book;
		this.borrower = borrower;
		this.borrowedAt = borrowedAt;
		this.returnedAt = returnedAt;
	}
}

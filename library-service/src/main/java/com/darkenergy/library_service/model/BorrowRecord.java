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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "borrow_records", indexes = {
    @Index(name = "idx_borrow_records_book", columnList = "book_id"),
    @Index(name = "idx_borrow_records_borrower", columnList = "borrower_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowRecord {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter 
	private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    @Getter @Setter 
    private Book book;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="borrower_id")
    @Getter @Setter 
    private Borrower borrower;

    @Column(nullable=false)
    @Getter @Setter 
    private Instant borrowedAt;

    @Column
    @Getter @Setter 
    private Instant returnedAt;
}

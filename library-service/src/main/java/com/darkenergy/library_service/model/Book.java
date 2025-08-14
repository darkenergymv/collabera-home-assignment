package com.darkenergy.library_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "books", indexes = {
	    @Index(name = "idx_books_isbn", columnList = "isbn")
	})
@Data
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String isbn;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private String author;
	
	@Version
	private Long version; // optional optimistic fallback

	public Book() {}

	public Book(Long id, String isbn, String title, String author) {
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}

}

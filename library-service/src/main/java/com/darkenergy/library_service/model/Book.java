package com.darkenergy.library_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books", indexes = {
	    @Index(name = "idx_books_isbn", columnList = "isbn")
	})
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	public Long getId() {
		return id;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public Long getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}

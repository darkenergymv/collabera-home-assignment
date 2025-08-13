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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	@Getter Long id;
	
	@Column(nullable=false)
	@Getter @Setter 
	private String isbn;
	
	@Column(nullable=false)
	@Getter @Setter 
	private String title;
	
	@Column(nullable=false)
	@Getter @Setter 
	private String author;
	
	@Version
	@Getter @Setter 
	private Long version; // optional optimistic fallback

}

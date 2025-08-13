package com.darkenergy.library_service.dto;

import lombok.Builder;

@Builder
public class BookResponseDTO {	

	private Long id;
	
    private String isbn;
	
    private String title;
	
    private String author;
	
	public BookResponseDTO() {}
	
	public BookResponseDTO(long l, String isbn, String title, String author) {
		this.id = l;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}

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
}

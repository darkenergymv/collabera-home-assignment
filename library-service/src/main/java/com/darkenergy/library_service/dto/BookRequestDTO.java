package com.darkenergy.library_service.dto;

import jakarta.validation.constraints.NotBlank;

public class BookRequestDTO {
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@NotBlank(message = "ISBN is required")
	private String isbn;
	
	@NotBlank(message = "Title is required")
    private String title;
	
	@NotBlank(message = "Author is required")
    private String author;

	public BookRequestDTO(@NotBlank(message = "ISBN is required") String isbn,
			@NotBlank(message = "Title is required") String title,
			@NotBlank(message = "Author is required") String author) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}

	public BookRequestDTO() {}
}

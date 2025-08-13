package com.darkenergy.library_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class BookRequestDTO {
	@NotBlank(message = "ISBN is required")
	@Getter @Setter 
	private String isbn;
	
	@NotBlank(message = "Title is required")
	@Getter @Setter 
    private String title;
	
	@NotBlank(message = "Author is required")
	@Getter @Setter 
    private String author;
}

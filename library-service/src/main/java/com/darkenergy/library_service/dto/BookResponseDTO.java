package com.darkenergy.library_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {
	@Getter @Setter 
	private Long id;
	
	@Getter @Setter 
    private String isbn;
	
	@Getter @Setter 
    private String title;
	
	@Getter @Setter 
    private String author;
}

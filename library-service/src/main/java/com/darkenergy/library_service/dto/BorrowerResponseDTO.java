package com.darkenergy.library_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowerResponseDTO {
	@Getter @Setter 
	private Long id;
	
	@Getter @Setter 
	private String name;
	
	@Getter @Setter 
	private String email;
}

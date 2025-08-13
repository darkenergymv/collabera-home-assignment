package com.darkenergy.library_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class BorrowerRequestDTO {
	
	@NotBlank(message = "Name is required")
	@Getter @Setter 
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Getter @Setter 
	private String email;
}

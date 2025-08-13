package com.darkenergy.library_service.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnResponseDTO {
	@Getter @Setter 
	private Long recordId;
	@Getter @Setter 
	private Long bookId;
	@Getter @Setter 
	private Long borrowerId;
	@Getter @Setter 
	private Instant borrowedAt;
	@Getter @Setter 
	private Instant returnedAt;

}

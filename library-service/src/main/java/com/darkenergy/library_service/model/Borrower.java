package com.darkenergy.library_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "borrowers", uniqueConstraints = {
	@UniqueConstraint(name = "uk_borrowers_email", columnNames = "email")
})
@Data
@Builder
public class Borrower {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false, unique=true)
	private String email;

	public Borrower() {	}

	public Borrower(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
}

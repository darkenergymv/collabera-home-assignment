package com.darkenergy.library_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "borrowers", uniqueConstraints = {
	    @UniqueConstraint(name = "uk_borrowers_email", columnNames = "email")
	})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Borrower {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter Long id;
	
	@Column(nullable=false)
	@Getter @Setter 
	private String name;
	
	@Column(nullable=false, unique=true)
	@Getter @Setter 
	private String email;

}

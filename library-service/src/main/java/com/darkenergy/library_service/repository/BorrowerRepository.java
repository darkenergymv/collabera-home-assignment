package com.darkenergy.library_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darkenergy.library_service.model.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long>{
	boolean existsByEmail(String email);
}

package com.darkenergy.library_service.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.darkenergy.library_service.model.Borrower;

@DataJpaTest
class BorrowerRepositoryTest {
	@Autowired
    BorrowerRepository borrowerRepository;		
	
	@Test
    void savingDuplicateEmail_shouldThrowDataIntegrityViolation() {
        borrowerRepository.saveAndFlush(
            Borrower.builder().name("Alice").email("alice@example.com").build()
        );
        
        Borrower b = Borrower.builder()
        		.name("Alicia")
        		.email("alice@example.com")
        		.build();

        assertThrows(
            DataIntegrityViolationException.class,
            () -> borrowerRepository.saveAndFlush(b)
        );
    }
	
	@Test
    void savingNullEmail_shouldThrowDataIntegrityViolation() {
		
		Borrower b = Borrower.builder()
        		.name("Alicia")
        		.build();
		
        assertThrows(
            DataIntegrityViolationException.class,
            () -> borrowerRepository.saveAndFlush(b)
        );
    }
	
	@Test
    void savingNullName_shouldThrowDataIntegrityViolation() {
		Borrower b = Borrower.builder()
        		.email("alice@example.com")
        		.build();
        assertThrows(
            DataIntegrityViolationException.class,
            () -> borrowerRepository.saveAndFlush(b)
        );
    }

}

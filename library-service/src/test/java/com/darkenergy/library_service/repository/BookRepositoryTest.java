package com.darkenergy.library_service.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.darkenergy.library_service.model.Book;

@DataJpaTest
class BookRepositoryTest {
	@Autowired
    BookRepository bookRepository;
	
	@Test
    void savingNullIsbn_shouldThrowDataIntegrityViolation() {
        Book b = new Book(null, null, "Clean Code", "Robert C. Martin");
        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.saveAndFlush(b));
    }
	
	@Test
    void savingNullTitle_shouldThrowDataIntegrityViolation() {
        Book b = new Book(null, "978-0132350884", null, "Robert C. Martin");
        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.saveAndFlush(b));
    }
	
	@Test
    void savingNullAuthor_shouldThrowDataIntegrityViolation() {
        Book b = new Book(null, "978-0132350884", "Clean Code", null);
        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.saveAndFlush(b));
    }
	
	@Test
    void existsByIsbn_shouldReturnTrueWhenPresent_falseWhenAbsent() {
        String isbn = "111-222-333";
        assertTrue(!bookRepository.existsByIsbn(isbn));

        bookRepository.saveAndFlush(new Book(null, isbn, "Title A", "Author A"));
        assertTrue(bookRepository.existsByIsbn(isbn));
    }
	
	@Test
    void findAllByIsbn_shouldReturnAllCopiesWithSameIsbn() {
        String isbn = "222-333-444";
        bookRepository.save(new Book(null, isbn, "Title A", "Author A"));
        bookRepository.save(new Book(null, isbn, "Title A", "Author A"));
        bookRepository.save(new Book(null, "different", "Other", "Other"));

        List<Book> copies = bookRepository.findAllByIsbn(isbn);
        assertEquals(2, copies.size());
        assertTrue(copies.stream().allMatch(b -> isbn.equals(b.getIsbn())));
    }
	
	@Test
    void version_shouldIncrementOnUpdate() {
        Book saved = bookRepository.saveAndFlush(new Book(null, "333-444-555", "Versioned", "Auth"));
        assertNotNull(saved.getVersion()); // Hibernate sets initial version (usually 0)

        Long v1 = saved.getVersion();
        saved.setTitle("Versioned - updated");
        Book updated = bookRepository.saveAndFlush(saved);

        assertNotNull(updated.getVersion());
        assertTrue(updated.getVersion() > v1);
    }
	
	@Test
    void findByIdForUpdate_shouldReturnBookInsideTransaction() {
        Book saved = bookRepository.saveAndFlush(new Book(null, "444-555-666", "Locked", "Auth"));
        var locked = bookRepository.findByIdForUpdate(saved.getId());
        // Sanity: we can fetch it and it’s present; full lock behavior is DB-specific and better verified with Testcontainers.
        assertTrue(locked.isPresent());
        assertEquals(saved.getId(), locked.get().getId());
    }

}

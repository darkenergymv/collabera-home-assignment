package com.darkenergy.library_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.darkenergy.library_service.model.Book;

import jakarta.persistence.EntityManager;

@DataJpaTest
class BookRepositoryOptimisticLockingTest {
	@Autowired
    private BookRepository bookRepository;

	@Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void testUpdateBookWithCorrectVersion_Succeeds() {
        // Arrange
        Book book = new Book();
        book.setIsbn("12345");
        book.setTitle("Domain Specific Languages");
        book.setAuthor("Martin Fowler");

        Book savedBook = bookRepository.save(book);
        entityManager.flush();
        entityManager.clear();

        // Act
        Book bookToUpdate = bookRepository.findById(savedBook.getId()).orElseThrow();
        bookToUpdate.setTitle("Domain Specific Languages - Updated");
        Book updatedBook = bookRepository.save(bookToUpdate);
        entityManager.flush();

        // Assert
        assertEquals(1L, updatedBook.getVersion()); // starts at 0, increments to 1
        assertEquals("Domain Specific Languages - Updated", updatedBook.getTitle());
    }
    
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED) // disables default test transaction
    void testUpdateBookWithStaleVersion_ThrowsOptimisticLockingFailureException() {
        // Arrange: persist a book
        Book book = new Book();
        book.setTitle("Book 1");
        book.setIsbn("12345");
        book.setAuthor("Author A");
        bookRepository.saveAndFlush(book);

        // Load two instances representing the same row
        Book book1 = bookRepository.findById(book.getId()).orElseThrow();
        Book book2 = bookRepository.findById(book.getId()).orElseThrow();

        // Update book1 and flush
        book1.setTitle("Updated Book 1");
        bookRepository.saveAndFlush(book1);

        // Now book2 is stale
        book2.setTitle("Updated Book 2");

        // Act + Assert
        assertThrows(OptimisticLockingFailureException.class, () -> {
            bookRepository.saveAndFlush(book2);
        });
    }

}

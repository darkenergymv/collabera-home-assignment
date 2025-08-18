package com.darkenergy.library_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.darkenergy.library_service.model.Book;
import com.darkenergy.library_service.model.BorrowRecord;
import com.darkenergy.library_service.model.Borrower;

@DataJpaTest
class BorrowRecordRepositoryTest {
	@Autowired
	BookRepository bookRepository;

	@Autowired
	BorrowerRepository borrowerRepository;

	@Autowired
	BorrowRecordRepository borrowRecordRepository;

	@BeforeEach
	void clearDatabase() {
		borrowRecordRepository.deleteAll();
		bookRepository.deleteAll();
		borrowerRepository.deleteAll();
	}

	@Test
	void testSaveBorrowRecord() {
		// Arrange
		Borrower borrower = Borrower.builder().name("Alice").email("alice@example.com").build();
		borrower = borrowerRepository.save(borrower);

		Book book = new Book();
		book.setIsbn("123-456");
		book.setTitle("Effective Java");
		book.setAuthor("Joshua Bloch");
		book = bookRepository.save(book);

		BorrowRecord br = new BorrowRecord();
		br.setBorrower(borrower);
		br.setBook(book);
		br.setBorrowedAt(Instant.now());

		// Act
		BorrowRecord saved = borrowRecordRepository.save(br);

		// Assert
		assertNotNull(saved.getId());
		assertEquals(borrower.getName(), saved.getBorrower().getName());
		assertEquals(book.getTitle(), saved.getBook().getTitle());
		assertEquals(borrower.getId(), saved.getBorrower().getId());
		assertEquals(book.getId(), saved.getBook().getId());
		assertEquals(null, saved.getReturnedAt());
	}

	@Test
	void savingRecordWithoutBorrowedAt_shouldThrowException() {
		Book book = bookRepository.save(new Book(null, "111-222", "Test Book", "Tester"));
		Borrower borrower = borrowerRepository
				.save(Borrower.builder().name("Alice").email("alice@example.com").build());

		BorrowRecord br = new BorrowRecord(null, book, borrower, null, null);

		assertThrows(DataIntegrityViolationException.class, () -> borrowRecordRepository.saveAndFlush(br));
	}

	@Test
	void savingAndQueryingActiveBorrowRecord_shouldWork() {
		Book book = bookRepository.save(new Book(null, "222-333", "Another Book", "Author"));
		Borrower borrower = borrowerRepository.save(Borrower.builder().name("Bob").email("bob@example.com").build());

		BorrowRecord saved = borrowRecordRepository.save(new BorrowRecord(null, book, borrower, Instant.now(), null));

		Optional<BorrowRecord> queried = borrowRecordRepository.findByBookAndReturnedAtIsNull(book);

		assertEquals(saved.getBook(), queried.get().getBook());
		assertEquals(saved.getBorrower(), queried.get().getBorrower());
		assertEquals(saved.getBorrowedAt(), queried.get().getBorrowedAt());
		assertEquals(saved.getReturnedAt(), queried.get().getReturnedAt());
	}
	
	@Test
	void testSameBorrowerCanBorrowDifferentBooks() {
	    // Arrange
		Borrower borrower = borrowerRepository
				.save(Borrower.builder().name("Alice").email("alice@example.com").build());
		
	    Book book1 = new Book(null, "111-000", "Book One", "Author A");
	    Book book2 = new Book(null, "222-000", "Book Two", "Author B");
	    book1 = bookRepository.save(book1);
	    book2 = bookRepository.save(book2);

	    BorrowRecord record1 = new BorrowRecord(null, book1, borrower, Instant.now(), null);
	    BorrowRecord record2 = new BorrowRecord(null, book2, borrower, Instant.now(), null);
	    borrowRecordRepository.save(record1);
	    borrowRecordRepository.save(record2);

	    // Act
	    List<BorrowRecord> records = borrowRecordRepository.findByBorrower(borrower);

	    // Assert
	    assertEquals(2, records.size());
	    assertTrue(records.stream().anyMatch(r -> r.getBook().getTitle().equals("Book One")));
	    assertTrue(records.stream().anyMatch(r -> r.getBook().getTitle().equals("Book Two")));
	}
	
	@Test
	void testReturnBookUpdatesReturnDate() {
	    // Arrange
	    Borrower borrower = borrowerRepository.save(
	    		Borrower.builder().name("Alice").email("alice@example.com").build());
	    Book book = bookRepository.save(new Book(null, "333-000", "Book Three", "Author C"));
	    BorrowRecord br = borrowRecordRepository.save(
	        new BorrowRecord(null, book, borrower, Instant.now(), null)
	    );

	    // Act
	    Instant returnedAt = Instant.now().plusSeconds(100000);
	    br.setReturnedAt(returnedAt);
	    borrowRecordRepository.save(br);

	    // Assert
	    BorrowRecord updated = borrowRecordRepository.findById(br.getId()).orElseThrow();
	    assertEquals(returnedAt, updated.getReturnedAt());
	}

}

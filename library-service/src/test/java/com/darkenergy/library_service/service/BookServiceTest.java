package com.darkenergy.library_service.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.darkenergy.library_service.dto.BookRequestDTO;
import com.darkenergy.library_service.dto.BookResponseDTO;
import com.darkenergy.library_service.model.Book;
import com.darkenergy.library_service.repository.BookRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {
	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	public BookServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegisterBook() {
		BookRequestDTO dto = new BookRequestDTO("12345", "Test Book", "Author Name");
		Book saved = new Book(1L, "12345", "Test Book", "Author Name");

		when(bookRepository.save(any(Book.class))).thenReturn(saved);

		BookResponseDTO result = bookService.registerBook(dto);

		assertNotNull(result);
		assertEquals("12345", result.getIsbn());
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	void testFindById() {
		Book book = new Book(1L, "12345", "Test Book", "Author Name");
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

		Optional<Book> found = bookService.findById(1L);

		assertTrue(found.isPresent());
		assertEquals("12345", found.get().getIsbn());
	}

}

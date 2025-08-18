package com.darkenergy.library_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.darkenergy.library_service.dto.BookRequestDTO;
import com.darkenergy.library_service.dto.BookResponseDTO;
import com.darkenergy.library_service.exception.BadRequestException;
import com.darkenergy.library_service.model.Book;
import com.darkenergy.library_service.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	void testRegisterBook_NewISBN_Success() {
	    // Arrange
	    BookRequestDTO requestDTO = new BookRequestDTO();
	    requestDTO.setIsbn("67890");
	    requestDTO.setTitle("Effective Java");
	    requestDTO.setAuthor("Joshua Bloch");

	    Book savedBook = new Book();
	    savedBook.setId(1L);
	    savedBook.setIsbn("67890");
	    savedBook.setTitle("Effective Java");
	    savedBook.setAuthor("Joshua Bloch");

	    when(bookRepository.existsByIsbn("67890")).thenReturn(false);
	    when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

	    // Act
	    BookResponseDTO responseDTO = bookService.registerBook(requestDTO);

	    // Assert
	    assertNotNull(responseDTO);
	    assertEquals("67890", responseDTO.getIsbn());
	    assertEquals("Effective Java", responseDTO.getTitle());
	    assertEquals("Joshua Bloch", responseDTO.getAuthor());
	    verify(bookRepository, times(1)).save(any(Book.class));
	}
	
	@Test
	void registerBook_existingIsbn_sameDetails_allowsAnotherCopy() {
	    // Arrange
	    var request = new BookRequestDTO();
	    request.setIsbn("12345");
	    request.setTitle("Clean Code");
	    request.setAuthor("Robert C. Martin");

	    when(bookRepository.existsByIsbn("12345")).thenReturn(true);
	    // copies found with same title/author -> no mismatch -> allowed
	    var existing = new Book(null, "12345", "Clean Code", "Robert C. Martin");
	    when(bookRepository.findAllByIsbn("12345")).thenReturn(List.of(existing));

	    var saved = new Book(1L, "12345", "Clean Code", "Robert C. Martin");
	    when(bookRepository.save(any(Book.class))).thenReturn(saved);

	    // Act
	    var response = bookService.registerBook(request);

	    // Assert
	    assertNotNull(response);
	    assertEquals("12345", response.getIsbn());
	    assertEquals("Clean Code", response.getTitle());
	    assertEquals("Robert C. Martin", response.getAuthor());
	    verify(bookRepository).existsByIsbn("12345");
	    verify(bookRepository).findAllByIsbn("12345");
	    verify(bookRepository).save(any(Book.class));
	}
	
	@Test
	void registerBook_existingIsbn_differentDetails_throwsBadRequest_andDoesNotSave() {
	    // Arrange
	    var request = new BookRequestDTO();
	    request.setIsbn("12345");
	    request.setTitle("Effective Java");
	    request.setAuthor("Joshua Bloch");

	    when(bookRepository.existsByIsbn("12345")).thenReturn(true);
	    // copies found but details mismatch -> should throw BadRequestException
	    var existing = new Book(null, "12345", "Clean Code", "Robert C. Martin");
	    when(bookRepository.findAllByIsbn("12345")).thenReturn(List.of(existing));

	    // Act + Assert
	    assertThrows(BadRequestException.class, () -> bookService.registerBook(request));
	    verify(bookRepository).existsByIsbn("12345");
	    verify(bookRepository).findAllByIsbn("12345");
	    verify(bookRepository, never()).save(any(Book.class));
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

package com.darkenergy.library_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.darkenergy.library_service.dto.BorrowRequestDTO;
import com.darkenergy.library_service.dto.BorrowResponseDTO;
import com.darkenergy.library_service.exception.BadRequestException;
import com.darkenergy.library_service.exception.NotFoundException;
import com.darkenergy.library_service.model.Book;
import com.darkenergy.library_service.model.BorrowRecord;
import com.darkenergy.library_service.model.Borrower;
import com.darkenergy.library_service.repository.BookRepository;
import com.darkenergy.library_service.repository.BorrowRecordRepository;
import com.darkenergy.library_service.repository.BorrowerRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {
	@Mock
    private BookRepository bookRepo;

    @Mock
    private BorrowerRepository borrowerRepo;

    @Mock
    private BorrowRecordRepository borrowRecordRepo;

    @InjectMocks
    private BorrowService borrowService;

    private Book book;
    private Borrower borrower;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);

        borrower = new Borrower();
        borrower.setId(1L);
    }

    // --- First test case: borrowBook when book is not found ---
    @Test
    void borrowBook_shouldThrowNotFound_whenBookNotFound() {
        BorrowRequestDTO request = new BorrowRequestDTO();
        request.setBookId(1L);
        request.setBorrowerId(1L);

        when(bookRepo.findByIdForUpdate(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> borrowService.borrowBook(request));

        assertEquals("Book not found", ex.getMessage());
        verify(bookRepo).findByIdForUpdate(1L);
        verifyNoMoreInteractions(bookRepo, borrowerRepo, borrowRecordRepo);
    }
    
    @Test
    void borrowBook_shouldThrowBadRequest_whenBookAlreadyBorrowed() {
        // Arrange
        BorrowRequestDTO request = new BorrowRequestDTO();
        request.setBookId(1L);
        request.setBorrowerId(10L);

        Book book = new Book();
        book.setId(1L);
        book.setIsbn("111-222");
        book.setTitle("Some Book");
        book.setAuthor("Some Author");

        when(bookRepo.findByIdForUpdate(1L)).thenReturn(Optional.of(book));
        when(borrowRecordRepo.existsByBookAndReturnedAtIsNull(book)).thenReturn(true);

        // Act + Assert
        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> borrowService.borrowBook(request));
        assertEquals("Book is already borrowed", ex.getMessage());

        // Verify interactions
        verify(bookRepo).findByIdForUpdate(1L);
        verify(borrowRecordRepo).existsByBookAndReturnedAtIsNull(book);
        verifyNoInteractions(borrowerRepo);                     // borrower lookup should not happen
        verify(borrowRecordRepo, never()).save(any(BorrowRecord.class)); // and no save
    }
    
//    @Test
//    void testBorrowBook_Success() {
//        BorrowRequestDTO request = new BorrowRequestDTO();
//        request.setBookId(1L);
//        request.setBorrowerId(1L);
//
//        Borrower borrower = new Borrower();
//        borrower.setId(1L);
//        borrower.setName("John Doe");
//
//        Book book = new Book();
//        book.setId(1L);
//        book.setTitle("Effective Java");
////        book.setAvailable(true);
//
//        when(borrowerRepo.findById(1L)).thenReturn(Optional.of(borrower));
//        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
//        when(borrowRecordRepo.save(any(BorrowRecord.class)))
//            .thenAnswer(invocation -> invocation.getArgument(0));
//
//        BorrowResponseDTO response = borrowService.borrowBook(request);
//
//        assertNotNull(response);
//        assertEquals("Effective Java", response.getBook().getTitle());
//        assertEquals("John Doe", response.getBorrower().getName());
//        assertEquals(1L, response.getBookId());
//        assertEquals(1L, response.getBorrowerId());
//
//        verify(borrowRecordRepo, times(1)).save(any(BorrowRecord.class));
//        verify(bookRepo, times(1)).save(book);
//    }


}

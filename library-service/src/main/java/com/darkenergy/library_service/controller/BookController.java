package com.darkenergy.library_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkenergy.library_service.dto.BookRequestDTO;
import com.darkenergy.library_service.dto.BookResponseDTO;
import com.darkenergy.library_service.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
	private final BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	/**
     * Register a new book
     */
    @PostMapping
    public ResponseEntity<BookResponseDTO> registerBook(@Valid @RequestBody BookRequestDTO request) {
    	BookResponseDTO response = bookService.registerBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieve all borrowers
     */
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

}

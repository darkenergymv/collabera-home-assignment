package com.darkenergy.library_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkenergy.library_service.dto.BorrowRequestDTO;
import com.darkenergy.library_service.dto.BorrowResponseDTO;
import com.darkenergy.library_service.dto.ReturnResponseDTO;
import com.darkenergy.library_service.service.BorrowService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowController {
	private static final Logger log = LoggerFactory.getLogger(BorrowController.class);
	private final BorrowService borrowService;
	
	public BorrowController(BorrowService borrowService) {
		this.borrowService = borrowService;
	}
    
    /**
     * Borrow a book.
     */
    @PostMapping
    public ResponseEntity<BorrowResponseDTO> borrowBook(@Valid @RequestBody BorrowRequestDTO request) {
    	log.info("DE: BorrowController -> borrowBook(): executed...");
    	return ResponseEntity.ok(borrowService.borrowBook(request));
    }
    
    /**
     * Return a book.
     */
    @PostMapping("/{bookId}/return")
    public ResponseEntity<ReturnResponseDTO> returnBook(@PathVariable Long bookId) {
    	log.info("DE: BorrowController -> returnBook(): executed...");
        return ResponseEntity.ok(borrowService.returnBook(bookId));
    }

}

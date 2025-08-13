package com.darkenergy.library_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkenergy.library_service.dto.BorrowerRequestDTO;
import com.darkenergy.library_service.dto.BorrowerResponseDTO;
import com.darkenergy.library_service.service.BorrowerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/borrowers")
public class BorrowerController {
	private final BorrowerService borrowerService;
	
	public BorrowerController(BorrowerService borrowerService) {
		this.borrowerService = borrowerService;
	}
	
	/**
     * Register a new borrower
     */
    @PostMapping
    public ResponseEntity<BorrowerResponseDTO> registerBorrower(@Valid @RequestBody BorrowerRequestDTO request) {
    	var dto = borrowerService.registerBorrower(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * Retrieve all borrowers
     */
    @GetMapping
    public ResponseEntity<List<BorrowerResponseDTO>> getAllBorrowers() {
        return ResponseEntity.ok(borrowerService.getAllBorrowers());
    }

}

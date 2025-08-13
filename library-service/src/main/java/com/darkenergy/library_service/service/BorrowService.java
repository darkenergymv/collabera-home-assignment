package com.darkenergy.library_service.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.darkenergy.library_service.dto.BorrowRequestDTO;
import com.darkenergy.library_service.dto.BorrowResponseDTO;
import com.darkenergy.library_service.dto.ReturnResponseDTO;
import com.darkenergy.library_service.exception.BadRequestException;
import com.darkenergy.library_service.exception.NotFoundException;
import com.darkenergy.library_service.model.Book;
import com.darkenergy.library_service.model.BorrowRecord;
import com.darkenergy.library_service.model.Borrower;
import com.darkenergy.library_service.repository.BookRepository;
import com.darkenergy.library_service.repository.BorrowRecordRepository;
import com.darkenergy.library_service.repository.BorrowerRepository;
import com.darkenergy.library_service.utils.AppUtils;

import jakarta.transaction.Transactional;

@Service
public class BorrowService {
	private static final Logger log = LoggerFactory.getLogger(BorrowService.class);
	private final BookRepository bookRepo;
    private final BorrowerRepository borrowerRepo;
    private final BorrowRecordRepository borrowRecordRepo;
    
    public BorrowService(BookRepository bookRepo, BorrowerRepository borrowerRepo,
                         BorrowRecordRepository borrowRecordRepo){
        this.bookRepo = bookRepo;
        this.borrowerRepo = borrowerRepo;
        this.borrowRecordRepo = borrowRecordRepo;
    }
    
    @Transactional
    public BorrowResponseDTO borrowBook(BorrowRequestDTO request) {
    	log.info("DE: BorrowService -> borrowBook(): executed...");
        Book book = bookRepo.findByIdForUpdate(request.getBookId())
            .orElseThrow(() -> new NotFoundException("Book not found"));

        if (borrowRecordRepo.existsByBookAndReturnedAtIsNull(book)) {
            throw new BadRequestException("Book is already borrowed");
        }

        Borrower borrower = borrowerRepo.findById(request.getBorrowerId())
            .orElseThrow(() -> new NotFoundException("Borrower not found"));

        BorrowRecord rec = new BorrowRecord();
        rec.setBook(book);
        rec.setBorrower(borrower);
        rec.setBorrowedAt(Instant.now());
        var saved = borrowRecordRepo.save(rec);
        
        return AppUtils.modelToDto(saved);
    }
    
    @Transactional
    public ReturnResponseDTO returnBook(Long bookId) {
    	Book book = bookRepo.findByIdForUpdate(bookId)
    			.orElseThrow(() -> new NotFoundException("Book not found"));

    	BorrowRecord active = borrowRecordRepo.findByBookAndReturnedAtIsNull(book)
    			.orElseThrow(() -> new BadRequestException("Book is not currently borrowed"));

    	active.setReturnedAt(Instant.now());
    	var saved = borrowRecordRepo.save(active);
    	return AppUtils.toReturnBookResponseDTO(saved);
    }

}

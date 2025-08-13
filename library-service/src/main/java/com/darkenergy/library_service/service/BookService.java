package com.darkenergy.library_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.darkenergy.library_service.dto.BookRequestDTO;
import com.darkenergy.library_service.dto.BookResponseDTO;
import com.darkenergy.library_service.exception.BadRequestException;
import com.darkenergy.library_service.model.Book;
import com.darkenergy.library_service.repository.BookRepository;
import com.darkenergy.library_service.utils.AppUtils;

import jakarta.validation.Valid;

@Service
public class BookService {
	private final BookRepository bookRepository;
	
	public BookService(BookRepository bookRespository) {
		this.bookRepository = bookRespository;
	}

	@Transactional
	public BookResponseDTO registerBook(@Valid BookRequestDTO request) {
		// If ISBN already exists, validate title & author consistency
		if(bookRepository.existsByIsbn(request.getIsbn())) {
			var copies = bookRepository.findAllByIsbn(request.getIsbn());
			
			var mismatch = copies.stream()
					.anyMatch(b -> !b.getTitle().equals(request.getTitle()) || !b.getAuthor().equals(request.getAuthor()));
			if (mismatch) {
				throw new BadRequestException("ISBN already used with different title/author");
			}
		}
		
		Book bookObj = AppUtils.dtoToModel(request);		
		var savedBook = bookRepository.save(bookObj);
		
		return AppUtils.modelToDto(savedBook);
	}

	@Transactional(readOnly = true)
	public List<BookResponseDTO> getAllBooks() {
		return bookRepository.findAll()
				.stream()
				.map(AppUtils::modelToDto)
				.toList();
	}

}

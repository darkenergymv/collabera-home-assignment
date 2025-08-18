package com.darkenergy.library_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.darkenergy.library_service.dto.BorrowerRequestDTO;
import com.darkenergy.library_service.dto.BorrowerResponseDTO;
import com.darkenergy.library_service.exception.BadRequestException;
import com.darkenergy.library_service.model.Borrower;
import com.darkenergy.library_service.repository.BorrowerRepository;
import com.darkenergy.library_service.utils.AppUtils;

import jakarta.validation.Valid;

@Service
public class BorrowerService {
	private static final Logger log = LoggerFactory.getLogger(BorrowerService.class);
	private final BorrowerRepository borrowerRepository;
	
	public BorrowerService(BorrowerRepository borrowerRepository) {
		this.borrowerRepository = borrowerRepository;
	}
	
	@Transactional
	public BorrowerResponseDTO registerBorrower(@Valid BorrowerRequestDTO request) {
		log.info("DE: BorrowerService -> registerBorrower(): method executed...");
		
		if(borrowerRepository.existsByEmail(request.getEmail())) {
			throw new BadRequestException("Email already registered");			
		}
		
		// Transform the DTO to Model object and save to DB
		Borrower borrowerObj = AppUtils.dtoToModel(request);
		Borrower savedBorrower = borrowerRepository.save(borrowerObj);
		
		// Transform the saved borrower and return the response DTO
		BorrowerResponseDTO responseDTO = AppUtils.modelToDto(savedBorrower);
		return responseDTO;
	}

	public List<BorrowerResponseDTO> getAllBorrowers() {
		return borrowerRepository.findAll()
				.stream()
				.map(AppUtils::modelToDto)
				.toList();
	}

}

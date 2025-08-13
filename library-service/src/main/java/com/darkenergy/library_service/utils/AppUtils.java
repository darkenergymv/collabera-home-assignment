package com.darkenergy.library_service.utils;

import org.springframework.beans.BeanUtils;

import com.darkenergy.library_service.dto.BorrowResponseDTO;
import com.darkenergy.library_service.dto.BookRequestDTO;
import com.darkenergy.library_service.dto.BookResponseDTO;
import com.darkenergy.library_service.dto.BorrowerRequestDTO;
import com.darkenergy.library_service.dto.BorrowerResponseDTO;
import com.darkenergy.library_service.dto.ReturnResponseDTO;
import com.darkenergy.library_service.model.Borrower;
import com.darkenergy.library_service.model.Book;
import com.darkenergy.library_service.model.BorrowRecord;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppUtils {
	public static Borrower dtoToModel(BorrowerRequestDTO dtoObj) {
		Borrower modelObj = new Borrower();
        BeanUtils.copyProperties(dtoObj, modelObj);
        return modelObj;
    }
	
	public static BorrowerResponseDTO modelToDto(Borrower modelObj) {
		BorrowerResponseDTO dtoObj = new BorrowerResponseDTO();
        BeanUtils.copyProperties(modelObj, dtoObj);
        return dtoObj;
    }

	public static Book dtoToModel(@Valid BookRequestDTO dtoObj) {
		Book modelObj = new Book();
		BeanUtils.copyProperties(dtoObj, modelObj);
		return modelObj;
	}
	
	public static BookResponseDTO modelToDto(@Valid Book modelObj) {
		BookResponseDTO dtoObj = new BookResponseDTO();
		BeanUtils.copyProperties(modelObj, dtoObj);
		return dtoObj;
	}

	public static BorrowResponseDTO modelToDto(BorrowRecord modelObj) {
		BorrowResponseDTO dtoObj = new BorrowResponseDTO();
		dtoObj.setBookId(modelObj.getBook().getId());
		
		BookResponseDTO book = new BookResponseDTO();
		book.setId(modelObj.getBook().getId());
		book.setIsbn(modelObj.getBook().getIsbn());
		book.setTitle(modelObj.getBook().getTitle());
		book.setAuthor(modelObj.getBook().getAuthor());
		dtoObj.setBook(book);
		
		dtoObj.setBorrowerId(modelObj.getBorrower().getId());
		
		BorrowerResponseDTO borrower = new BorrowerResponseDTO();
		borrower.setId(modelObj.getBorrower().getId());
		borrower.setName(modelObj.getBorrower().getName());
		borrower.setEmail(modelObj.getBorrower().getEmail());
		dtoObj.setBorrower(borrower);		
		
		return dtoObj;
	}
	
	public static ReturnResponseDTO toReturnBookResponseDTO(BorrowRecord modelObj) {
		ReturnResponseDTO dtoObj = new ReturnResponseDTO();
		BeanUtils.copyProperties(modelObj, dtoObj);
		
		dtoObj.setRecordId(modelObj.getId());
		dtoObj.setBookId(modelObj.getBook().getId());
		dtoObj.setBorrowerId(modelObj.getBorrower().getId());
		dtoObj.setBorrowedAt(modelObj.getBorrowedAt());
		dtoObj.setReturnedAt(modelObj.getReturnedAt());		
		
		return dtoObj;
	}

}

package com.darkenergy.library_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darkenergy.library_service.model.Book;
import com.darkenergy.library_service.model.BorrowRecord;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
	boolean existsByBookAndReturnedAtIsNull(Book book);
	Optional<BorrowRecord> findByBookAndReturnedAtIsNull(Book book);

}

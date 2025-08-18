package com.darkenergy.library_service.service;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.darkenergy.library_service.dto.BorrowerRequestDTO;
import com.darkenergy.library_service.dto.BorrowerResponseDTO;
import com.darkenergy.library_service.exception.BadRequestException;
import com.darkenergy.library_service.model.Borrower;
import com.darkenergy.library_service.repository.BorrowerRepository;
import com.darkenergy.library_service.utils.AppUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowerServiceTest {
	@Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerService borrowerService;

    private BorrowerRequestDTO requestDTO;
    private Borrower borrower;
    private BorrowerResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new BorrowerRequestDTO();
        requestDTO.setName("Charles Hoskinson");
        requestDTO.setEmail("charles@example.com");

        borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("Charles Hoskinson");
        borrower.setEmail("charles@example.com");

        responseDTO = new BorrowerResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Charles Hoskinson");
        responseDTO.setEmail("charles@example.com");
    }

    @Test
    void registerBorrower_WhenEmailExists_ShouldThrowBadRequestException() {
        // Arrange
        when(borrowerRepository.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> borrowerService.registerBorrower(requestDTO));
        verify(borrowerRepository, never()).save(any(Borrower.class));
    }

    @Test
    void registerBorrower_WhenEmailDoesNotExist_ShouldSaveAndReturnDTO() {
        // Arrange
        when(borrowerRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        // ⚠️ Stub AppUtils methods (static) using mockStatic
        try (var mocked = mockStatic(AppUtils.class)) {
            mocked.when(() -> AppUtils.dtoToModel(requestDTO)).thenReturn(borrower);
            mocked.when(() -> AppUtils.modelToDto(borrower)).thenReturn(responseDTO);

            // Act
            BorrowerResponseDTO result = borrowerService.registerBorrower(requestDTO);

            // Assert
            assertNotNull(result);
            assertEquals("Charles Hoskinson", result.getName());
            assertEquals("charles@example.com", result.getEmail());
            verify(borrowerRepository, times(1)).save(borrower);
        }
    }

    @Test
    void getAllBorrowers_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        when(borrowerRepository.findAll()).thenReturn(List.of());

        // Act
        List<BorrowerResponseDTO> result = borrowerService.getAllBorrowers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllBorrowers_WhenBorrowersExist_ShouldReturnDTOList() {
        // Arrange
        when(borrowerRepository.findAll()).thenReturn(List.of(borrower));

        try (var mocked = mockStatic(AppUtils.class)) {
            mocked.when(() -> AppUtils.modelToDto(borrower)).thenReturn(responseDTO);

            // Act
            List<BorrowerResponseDTO> result = borrowerService.getAllBorrowers();

            // Assert
            assertEquals(1, result.size());
            assertEquals("Charles Hoskinson", result.get(0).getName());
            assertEquals("charles@example.com", result.get(0).getEmail());
        }
    }

}

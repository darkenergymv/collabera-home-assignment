package com.darkenergy.library_service.controller;

import com.darkenergy.library_service.dto.BookRequestDTO;
import com.darkenergy.library_service.dto.BookResponseDTO;
import com.darkenergy.library_service.service.BookService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BookController.class})
class BookControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  @MockBean BookService bookService;

  @Test
  void create_and_list() throws Exception {
    var req = new BookRequestDTO(); 
    req.setIsbn("I"); req.setTitle("T"); req.setAuthor("A");
    var resp = new BookResponseDTO(1L, "I", "T", "A");
    Mockito.when(bookService.registerBook(any())).thenReturn(resp);
    Mockito.when(bookService.getAllBooks()).thenReturn(List.of(resp));

    mvc.perform(post("/api/v1/books")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(req)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));

    mvc.perform(get("/api/v1/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].isbn").value("I"));
  }
}

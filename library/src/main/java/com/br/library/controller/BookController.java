package com.br.library.controller;

import com.br.library.dto.request.BookRequestDTO;
import com.br.library.dto.response.BookResponseDTO;
import com.br.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(
            summary = "Recupera todos os livros da biblioteca."
    )
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(
            summary = "Recupera um livro da biblioteca pelo ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO bookResponseDTO = bookService.getBookById(id);
        return ResponseEntity.ok(bookResponseDTO);
    }

    @Operation(
            summary = "Cria um novo livro na biblioteca."
    )
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequestDTO) {
        BookResponseDTO bookResponseDTO = bookService.createBook(bookRequestDTO);
        return ResponseEntity.ok(bookResponseDTO);
    }

    @Operation(
            summary = "Atualiza um livro na biblioteca pelo ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO bookRequestDTO) {
        BookResponseDTO bookResponseDTO = bookService.updateBook(id, bookRequestDTO);
        return ResponseEntity.ok(bookResponseDTO);
    }

    @Operation(
            summary = "Remove um livro da biblioteca pelo ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book with id " + id + " has been deleted.");
    }
}

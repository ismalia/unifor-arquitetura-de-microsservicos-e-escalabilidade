package com.br.library.service;

import com.br.library.dto.request.BookRequestDTO;
import com.br.library.dto.response.BookResponseDTO;
import com.br.library.entity.Book;
import com.br.library.exception.BookNotFoundException;
import com.br.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookResponseDTO> getAllBooks() {
        logger.info("Starting to get all books...");
        List<Book> books = bookRepository.findAll();
        logger.info("Finished getting all books.");
        return books.stream()
                .map(book -> new BookResponseDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublicationYear(), book.getAvailableCopies()))
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBookById(Long id) {
        logger.info("Starting to get book with id {}...", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found."));
        logger.info("Finished getting book with id {}.", book.getId());
        return new BookResponseDTO(book.getId(),book.getTitle(),book.getAuthor(), book.getIsbn(), book.getPublicationYear(), book.getAvailableCopies());
    }

    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
        Book book = new Book(bookRequestDTO.getTitle(), bookRequestDTO.getAuthor(), bookRequestDTO.getIsbn(), bookRequestDTO.getPublicationYear(), bookRequestDTO.getAvailableCopies());
        logger.info("Creating a new book with this information -> Title: {}, Author: {}, ISBN: {}, Publication year: {} and Available copies: {}.", book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublicationYear(), book.getAvailableCopies());
        Book savedBook = bookRepository.save(book);
        logger.info("Finished creating a book. Book ID: {}.", savedBook.getId());
        return new BookResponseDTO(savedBook.getId(), savedBook.getTitle(), savedBook.getAuthor(), savedBook.getIsbn(), savedBook.getPublicationYear(), book.getAvailableCopies());
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found."));
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setIsbn(bookRequestDTO.getIsbn());
        book.setPublicationYear(bookRequestDTO.getPublicationYear());
        book.setAvailableCopies(bookRequestDTO.getAvailableCopies());
        logger.info("Updating book id {} with this information -> Title: {}, Author: {}, ISBN: {}, Publication year: {} and Available copies: {}.", id, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublicationYear(), book.getAvailableCopies());
        Book updatedBook = bookRepository.save(book);
        logger.info("Finished updating book with id {}.", updatedBook.getId());
        return new BookResponseDTO(updatedBook.getId(), updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getIsbn(), updatedBook.getPublicationYear(), book.getAvailableCopies());
    }

    public void deleteBook(Long id) {
        logger.info("Starting to delete book with id {}...", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found."));
        bookRepository.delete(book);
        logger.info("Finished deleting book with id {}.", book.getId());
    }
}

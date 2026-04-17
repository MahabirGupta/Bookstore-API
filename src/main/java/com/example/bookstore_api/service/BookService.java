package com.example.bookstore_api.service;


import com.example.bookstore_api.dto.AuthorRequest;
import com.example.bookstore_api.dto.BookRequest;
import com.example.bookstore_api.entity.Author;
import com.example.bookstore_api.entity.Book;
import com.example.bookstore_api.exception.DuplicateResourceException;
import com.example.bookstore_api.exception.InvalidRequestException;
import com.example.bookstore_api.exception.ResourceNotFoundException;
import com.example.bookstore_api.repository.AuthorRepository;
import com.example.bookstore_api.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book addBook(BookRequest request) {
        if (bookRepository.existsById(request.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN " + request.getIsbn() + " already exists");
        }

        Book book = new Book();
        mapRequestToBook(request, book);
        return bookRepository.save(book);
    }

    public Book updateBook(String isbn, BookRequest request) {
        Book existingBook = bookRepository.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));

        if (!isbn.equals(request.getIsbn())) {
            throw new InvalidRequestException("Path ISBN and request ISBN must match");
        }

        mapRequestToBook(request, existingBook);
        return bookRepository.save(existingBook);
    }

    @Transactional(readOnly = true)
    public List<Book> searchBooks(String title, String authorName) {
        if ((title == null || title.isBlank()) && (authorName == null || authorName.isBlank())) {
            throw new InvalidRequestException("Provide title or authorName or both");
        }

        String normalizedTitle = normalize(title);
        String normalizedAuthorName = normalize(authorName);

        return bookRepository.searchExact(normalizedTitle, normalizedAuthorName);
    }

    public void deleteBook(String isbn) {
        Book existingBook = bookRepository.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));
        bookRepository.delete(existingBook);
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));
    }

    private void mapRequestToBook(BookRequest request, Book book) {
        book.setIsbn(request.getIsbn().trim());
        book.setTitle(request.getTitle().trim());
        book.setYear(request.getYear());
        book.setPrice(request.getPrice());
        book.setGenre(request.getGenre().trim());

        Set<Author> resolvedAuthors = new HashSet<>();
        for (AuthorRequest authorRequest : request.getAuthors()) {
            String name = authorRequest.getName().trim();

            Author author = authorRepository
                    .findByNameAndBirthday(name, authorRequest.getBirthday())
                    .orElseGet(() -> new Author(name, authorRequest.getBirthday()));

            resolvedAuthors.add(author);
        }

        book.getAuthors().clear();
        book.getAuthors().addAll(resolvedAuthors);
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}

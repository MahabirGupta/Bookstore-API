package com.example.bookstore_api.config;

import com.example.bookstore_api.dto.AuthorRequest;
import com.example.bookstore_api.dto.BookRequest;
import com.example.bookstore_api.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadSampleData(BookService bookService) {
        return args -> {
            if (bookService.getAllBooks().isEmpty()) {
                BookRequest book1 = new BookRequest();
                book1.setIsbn("9780134685991");
                book1.setTitle("Effective Java");
                book1.setYear(2018);
                book1.setPrice(45.50);
                book1.setGenre("Programming");

                AuthorRequest a1 = new AuthorRequest();
                a1.setName("Joshua Bloch");
                a1.setBirthday(LocalDate.of(1961, 8, 28));
                book1.setAuthors(List.of(a1));

                bookService.addBook(book1);

                BookRequest book2 = new BookRequest();
                book2.setIsbn("9781617294945");
                book2.setTitle("Spring in Action");
                book2.setYear(2018);
                book2.setPrice(52.00);
                book2.setGenre("Programming");

                AuthorRequest a2 = new AuthorRequest();
                a2.setName("Craig Walls");
                a2.setBirthday(LocalDate.of(1971, 1, 1));
                book2.setAuthors(List.of(a2));

                bookService.addBook(book2);
            }
        };
    }
}

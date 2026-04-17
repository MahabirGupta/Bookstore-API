package com.example.bookstore_api.repository;

import com.example.bookstore_api.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameAndBirthday(String name, LocalDate birthday);
}

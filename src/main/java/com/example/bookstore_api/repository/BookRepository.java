package com.example.bookstore_api.repository;

import com.example.bookstore_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query("""
        select distinct b
        from Book b
        left join b.authors a
        where (:title is null or b.title = :title)
          and (:authorName is null or a.name = :authorName)
    """)
    List<Book> searchExact(String title, String authorName);
}

package com.example.bookstore_api.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public class BookRequest {

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Authors are required")
    @Size(min = 1, message = "At least one author is required")
    @Valid
    private List<AuthorRequest> authors;

    @NotNull(message = "Year is required")
    @Min(value = 1000, message = "Year must be valid")
    @Max(value = 9999, message = "Year must be valid")
    private Integer year;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Genre is required")
    private String genre;

    public BookRequest() {
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<AuthorRequest> getAuthors() {
        return authors;
    }

    public Integer getYear() {
        return year;
    }

    public Double getPrice() {
        return price;
    }

    public String getGenre() {
        return genre;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<AuthorRequest> authors) {
        this.authors = authors;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

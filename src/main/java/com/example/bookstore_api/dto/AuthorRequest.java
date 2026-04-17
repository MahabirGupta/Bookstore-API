package com.example.bookstore_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AuthorRequest {

    @NotBlank(message = "Author name is required")
    private String name;

    @NotNull(message = "Author birthday is required")
    private LocalDate birthday;

    public AuthorRequest() {
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}

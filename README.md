# Bookstore REST API

A Spring Boot RESTful API for managing books and authors with authentication, validation, and role-based authorization.

---

##  Features

* Add, update, delete books
* Search books by title and/or author (exact match)
* Multiple authors per book
* Validation with proper error handling
* Role-based security (USER / ADMIN)
* H2 in-memory database
* REST API (no UI)

---

## Authentication

| Role  | Username | Password | Access            |
| ----- | -------- | -------- | ----------------- |
| USER  | bookuser | user123  | All except DELETE |
| ADMIN | admin    | admin123 | Full access       |

---

## Base URL

```
http://localhost:8080
```

---

##  Test Cases (Insomnia / Postman)

### 1. Get All Books

| Field  | Value        |
| ------ | ------------ |
| Method | GET          |
| URL    | `/api/books` |
| Auth   | USER         |

**Expected:** 200 OK

---

### 2. Get Book by ISBN

| Field   | Value                      |
| ------- | -------------------------- |
| Method  | GET                        |
| URL     | `/api/books/{isbn}`        |
| Example | `/api/books/9780134685991` |
| Auth    | USER                       |

**Expected:** 200 OK

---

### 3. Add New Book

| Field  | Value        |
| ------ | ------------ |
| Method | POST         |
| URL    | `/api/books` |
| Auth   | USER         |

**Body:**

```json
{
  "isbn": "9780321356680",
  "title": "Java Concurrency in Practice",
  "authors": [
    {
      "name": "Brian Goetz",
      "birthday": "1969-02-19"
    }
  ],
  "year": 2006,
  "price": 49.99,
  "genre": "Programming"
}
```

**Expected:** 201 Created

---

### 4. Add Duplicate Book

| Field  | Value        |
| ------ | ------------ |
| Method | POST         |
| URL    | `/api/books` |
| Auth   | USER         |

**Expected:** 409 Conflict

---

### 5. Add Book with Invalid Title

| Field  | Value        |
| ------ | ------------ |
| Method | POST         |
| URL    | `/api/books` |
| Auth   | USER         |

**Body:**

```json
{
  "isbn": "9999999999999",
  "title": " ",
  "authors": [
    {
      "name": "Some Author",
      "birthday": "1980-01-01"
    }
  ],
  "year": 2024,
  "price": 19.99,
  "genre": "Fiction"
}
```

**Expected:** 400 Bad Request

---

### 6. Update Book

| Field  | Value               |
| ------ | ------------------- |
| Method | PUT                 |
| URL    | `/api/books/{isbn}` |
| Auth   | USER                |

**Expected:** 200 OK

---

### 7. Update Book Not Found

| Field  | Value                      |
| ------ | -------------------------- |
| Method | PUT                        |
| URL    | `/api/books/1111111111111` |
| Auth   | USER                       |

**Expected:** 404 Not Found

---

### 8. Update ISBN Mismatch

| Field  | Value                      |
| ------ | -------------------------- |
| Method | PUT                        |
| URL    | `/api/books/1111111111111` |
| Auth   | USER                       |

**Expected:** 400 Bad Request

---

### 9. Search by Title

| Field  | Value                                      |
| ------ | ------------------------------------------ |
| Method | GET                                        |
| URL    | `/api/books/search?title=Effective%20Java` |
| Auth   | USER                                       |

**Expected:** 200 OK

---

### 10. Search by Author

| Field  | Value                                         |
| ------ | --------------------------------------------- |
| Method | GET                                           |
| URL    | `/api/books/search?authorName=Joshua%20Bloch` |
| Auth   | USER                                          |

**Expected:** 200 OK

---

### 11. Search by Title & Author

| Field  | Value                                                                |
| ------ | -------------------------------------------------------------------- |
| Method | GET                                                                  |
| URL    | `/api/books/search?title=Effective%20Java&authorName=Joshua%20Bloch` |
| Auth   | USER                                                                 |

**Expected:** 200 OK

---

### 12. Search Without Parameters

| Field  | Value               |
| ------ | ------------------- |
| Method | GET                 |
| URL    | `/api/books/search` |
| Auth   | USER                |

**Expected:** 400 Bad Request

---

### 13. Delete Book (USER)

| Field  | Value               |
| ------ | ------------------- |
| Method | DELETE              |
| URL    | `/api/books/{isbn}` |
| Auth   | USER                |

**Expected:** 403 Forbidden

---

### 14. Delete Book (ADMIN)

| Field  | Value               |
| ------ | ------------------- |
| Method | DELETE              |
| URL    | `/api/books/{isbn}` |
| Auth   | ADMIN               |

**Expected:** 204 No Content

---

### 15. Delete Book Not Found

| Field  | Value                      |
| ------ | -------------------------- |
| Method | DELETE                     |
| URL    | `/api/books/9999999999999` |
| Auth   | ADMIN                      |

**Expected:** 404 Not Found

---

### 16. Unauthorized Access

| Field  | Value        |
| ------ | ------------ |
| Method | GET          |
| URL    | `/api/books` |
| Auth   | NONE         |

**Expected:** 401 Unauthorized

---

## 🗄️ H2 Database Console

```
http://localhost:8080/h2-console
```

| Field    | Value                   |
| -------- | ----------------------- |
| JDBC URL | jdbc:h2:mem:bookstoredb |
| Username | sa                      |
| Password | (leave blank)           |

---

## ⚙️ How to Run

```bash
mvn spring-boot:run
```

---

## 🧠 Notes

* In-memory DB resets on restart
* DELETE endpoint restricted to ADMIN role
* Validation handled using Jakarta Validation + Hibernate Validator
* Errors handled using GlobalExceptionHandler

---

package com.example.librarymangesystem.repository;

import com.example.librarymangesystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

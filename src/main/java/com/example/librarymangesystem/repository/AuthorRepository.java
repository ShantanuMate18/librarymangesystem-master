package com.example.librarymangesystem.repository;



import com.example.librarymangesystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

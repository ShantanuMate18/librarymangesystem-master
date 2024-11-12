package com.example.librarymangesystem.repository;


import com.example.librarymangesystem.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}

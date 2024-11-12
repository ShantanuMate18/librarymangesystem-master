package com.example.librarymangesystem.controller;

import com.example.librarymangesystem.model.Borrower;
import com.example.librarymangesystem.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @GetMapping
    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/{id}")
    public Optional<Borrower> getBorrowerById(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id);
    }

    @PostMapping
    public Borrower createBorrower(@RequestBody Borrower borrower) {
        return borrowerService.createBorrower(borrower);
    }

    @PutMapping("/{id}")
    public Borrower updateBorrower(@PathVariable Long id, @RequestBody Borrower borrower) {
        return borrowerService.updateBorrower(id, borrower);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBorrower(@PathVariable Long id) {
        return borrowerService.deleteBorrower(id);
    }
}

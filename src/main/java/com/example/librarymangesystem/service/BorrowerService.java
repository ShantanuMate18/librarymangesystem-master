package com.example.librarymangesystem.service;

import com.example.librarymangesystem.model.Borrower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Borrower> getAllBorrowers() {
        String sql = "SELECT * FROM borrower";
        return jdbcTemplate.query(sql, new BorrowerRowMapper());
    }

    public Optional<Borrower> getBorrowerById(Long id) {
        String sql = "SELECT * FROM borrower WHERE id = ?";
        Borrower borrower = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BorrowerRowMapper());
        return Optional.ofNullable(borrower);
    }

    public Borrower createBorrower(Borrower borrower) {
        String sql = "INSERT INTO borrower (name, email, phone_number) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, borrower.getName(), borrower.getEmail(), borrower.getPhoneNumber());
        // Return the created borrower with the id populated (assuming it's auto-incremented)
        String query = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(query, Long.class);
        borrower.setId(id);
        return borrower;
    }

    public Borrower updateBorrower(Long id, Borrower borrower) {
        String sql = "UPDATE borrower SET name = ?, email = ?, phone_number = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, borrower.getName(), borrower.getEmail(), borrower.getPhoneNumber(), id);
        if (rowsAffected > 0) {
            borrower.setId(id);
            return borrower;
        }
        return null;
    }

    public boolean deleteBorrower(Long id) {
        String sql = "DELETE FROM borrower WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    // RowMapper to map rows from the result set to Borrower objects
    private static final class BorrowerRowMapper implements RowMapper<Borrower> {
        @Override
        public Borrower mapRow(ResultSet rs, int rowNum) throws SQLException {
            Borrower borrower = new Borrower();
            borrower.setId(rs.getLong("id"));
            borrower.setName(rs.getString("name"));
            borrower.setEmail(rs.getString("email"));
            borrower.setPhoneNumber(rs.getString("phone_number"));
            return borrower;
        }
    }
}

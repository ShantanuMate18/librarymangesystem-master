package com.example.librarymangesystem.service;

import com.example.librarymangesystem.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    public Optional<Book> getBookById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Book book = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BookRowMapper());
        return Optional.ofNullable(book);
    }

    public Book createBook(Book book) {
        String sql = "INSERT INTO book (title, author, isbn, publication_date, status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublicationDate(), book.getStatus());
        // Return the created book with the id populated (assuming it's auto-incremented)
        String query = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(query, Long.class);
        book.setId(id);
        return book;
    }

    public Book updateBook(Long id, Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, isbn = ?, publication_date = ?, status = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublicationDate(), book.getStatus(), id);
        if (rowsAffected > 0) {
            book.setId(id);
            return book;
        }
        return null;
    }

    public boolean deleteBook(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    // RowMapper to map rows from the result set to Book objects
    private static final class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setIsbn(rs.getString("isbn"));
            book.setPublicationDate(rs.getDate("publication_date").toLocalDate());
            book.setStatus(rs.getString("status"));
            return book;
        }
    }
}

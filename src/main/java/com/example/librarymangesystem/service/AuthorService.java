package com.example.librarymangesystem.service;

import com.example.librarymangesystem.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Author> getAllAuthors() {
        String sql = "SELECT * FROM author";
        return jdbcTemplate.query(sql, new AuthorRowMapper());
    }

    public Optional<Author> getAuthorById(Long id) {
        String sql = "SELECT * FROM author WHERE id = ?";
        Author author = jdbcTemplate.queryForObject(sql, new Object[]{id}, new AuthorRowMapper());
        return Optional.ofNullable(author);
    }

    public Author createAuthor(Author author) {
        String sql = "INSERT INTO author (name, date_of_birth, nationality) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, author.getName(), author.getDateOfBirth(), author.getNationality());
        // Return the created author with the id populated (assuming it's auto-incremented)
        String query = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(query, Long.class);
        author.setId(id);
        return author;
    }

    public Author updateAuthor(Long id, Author author) {
        String sql = "UPDATE author SET name = ?, date_of_birth = ?, nationality = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, author.getName(), author.getDateOfBirth(), author.getNationality(), id);
        if (rowsAffected > 0) {
            author.setId(id);
            return author;
        }
        return null;
    }

    public boolean deleteAuthor(Long id) {
        String sql = "DELETE FROM author WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    // RowMapper to map rows from the result set to Author objects
    private static final class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            author.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
            author.setNationality(rs.getString("nationality"));
            return author;
        }
    }
}

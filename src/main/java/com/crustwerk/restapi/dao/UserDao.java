package com.crustwerk.restapi.dao;

import com.crustwerk.restapi.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mappatore per User
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPasswordHash(rs.getString("passwordHash"));
            user.setDateOfBirth(rs.getObject("dateOfBirth", java.time.LocalDate.class));
            user.setCreatedAt(rs.getObject("createdAt", java.time.LocalDate.class));
            user.setLastUpdateAt(rs.getObject("lastUpdateAt", java.time.LocalDate.class));
            return user;
        }
    }

    // Metodo 1: existsByEmail (verifica se un'email esiste nel DB)
    public boolean existsByEmail(String email) {
        String sql = """
                    SELECT COUNT(*) FROM "user"
                    WHERE "email" = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    // Metodo 2: getUserById
    public User getUserById(Long id) {
        String sql = """
                    SELECT * FROM "user"
                    WHERE "id" = ?
                """;
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
    }

    // Metodo 3: addUser
    public void addUser(User user) {
        String sql = """
                    INSERT INTO "user" ("username", "email", "passwordHash", "dateOfBirth", "createdAt", "lastUpdateAt")
                    VALUES (?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPasswordHash(), user.getDateOfBirth(), user.getCreatedAt(), user.getLastUpdateAt());
    }

    // Metodo 4: getAllUsers
    public List<User> getAllUsers() {
        String sql = """
                    SELECT * FROM "user"
                """;
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // Metodo 5: updateUser
    public void updateUser(User user) {
        String sql = """
                    UPDATE "user" 
                    SET "username" = ?, "email" = ?, "passwordHash" = ?, "dateOfBirth" = ?, "createdAt" = ?, "lastUpdateAt" = ?
                    WHERE "id" = ?
                """;
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPasswordHash(), user.getDateOfBirth(), user.getCreatedAt(), user.getLastUpdateAt(), user.getId());
    }

    // Metodo 6: deleteUser
    public void deleteUser(Long id) {
        String sql = """
                    DELETE FROM "user"
                    WHERE "id" = ?
                """;
        jdbcTemplate.update(sql, id);
    }
}

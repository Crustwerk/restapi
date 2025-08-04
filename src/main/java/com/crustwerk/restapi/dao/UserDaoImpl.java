package com.crustwerk.restapi.dao;

import com.crustwerk.restapi.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByEmail(String email) {
        String sql = """
                    SELECT COUNT(*) FROM "user"
                    WHERE "email" = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    public User getUserById(Long id) {
        String sql = """
                    SELECT * FROM "user"
                    WHERE "id" = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public long createUser(User user) {

        String sql = """
                    INSERT INTO "user" ("username", "email", "password_hash", "date_of_birth", "created_at", "last_update_at")
                    VALUES (?, ?, ?, ?, ?, ?)
                """;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setDate(4, Date.valueOf(user.getDateOfBirth()));
            ps.setDate(5, Date.valueOf(user.getCreatedAt()));
            ps.setDate(6, Date.valueOf(user.getLastUpdateAt()));
            return ps;
        }, keyHolder);

        //TODO: valutare creazione nuova eccezione da throware in caso di NPE
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<User> getAllUsers() {
        String sql = """
                    SELECT * FROM "user"
                """;
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public void updateUser(User user) {
        String sql = """
                    UPDATE "user"
                    SET "username" = ?, "email" = ?, "password_hash" = ?, "date_of_birth" = ?, "created_at" = ?, "last_update_at" = ?
                    WHERE "id" = ?
                """;
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPasswordHash(), user.getDateOfBirth(), user.getCreatedAt(), user.getLastUpdateAt(), user.getId());
    }

    public void deleteUser(Long id) {
        String sql = """
                    DELETE FROM "user"
                    WHERE "id" = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    // Mappatore per User
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPasswordHash(rs.getString("password_hash"));
            user.setDateOfBirth(rs.getObject("date_of_birth", java.time.LocalDate.class));
            user.setCreatedAt(rs.getObject("created_at", java.time.LocalDate.class));
            user.setLastUpdateAt(rs.getObject("last_update_at", java.time.LocalDate.class));
            return user;
        }
    }
}

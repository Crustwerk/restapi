package com.crustwerk.restapi.dao;

import com.crustwerk.restapi.model.Subscription;
import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SubscriptionDaoImpl implements SubscriptionDao {

    private final JdbcTemplate jdbcTemplate;

    public SubscriptionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Subscription> getSubscriptionsByStartAfterAndEndBefore(LocalDate start, LocalDate end) {
        String sql = """
                    SELECT * FROM "subscription"
                    WHERE "start" > ? AND "end" < ?
                """;
        return jdbcTemplate.query(sql, new Object[]{start, end}, new SubscriptionRowMapper());
    }

    public List<Subscription> getAllSubscriptionByFilter(LocalDate start, LocalDate end) {
        String sql = """
                    SELECT * FROM "subscription" s
                    WHERE
                    (s."start" BETWEEN ? AND ? OR s."end" BETWEEN ? AND ?)
                    OR
                    (? BETWEEN s."start" AND s."end" OR ? BETWEEN s."start" AND s."end")
                """;
        return jdbcTemplate.query(sql, new Object[]{start, end, start, end, start, end}, new SubscriptionRowMapper());
    }

    public Subscription getSubscriptionById(Long id) {
        String sql = """
                    SELECT * FROM "subscription"
                    WHERE "id" = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new SubscriptionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null; // Nessun risultato trovato
        }
    }

    public void addSubscription(Subscription subscription) {
        String sql = """
                    INSERT INTO "subscription" ("start", "end", "subscription_tier", "subscription_duration")
                    VALUES (?, ?, ?, ?)
                """;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(subscription.getStart()));
            ps.setDate(2, Date.valueOf(subscription.getEnd()));
            ps.setString(3, subscription.getSubscriptionTier().name());
            ps.setString(4, subscription.getSubscriptionDuration().name());
            return ps;
        }, keyHolder);

        // Imposta l'ID generato nella subscription
        subscription.setId(keyHolder.getKey().longValue());
    }

    public List<Subscription> getAllSubscriptions() {
        String sql = """
                SELECT *  FROM "subscription"
                """;
        return jdbcTemplate.query(sql, new SubscriptionRowMapper());
    }

    // Mappatore per Subscription
    private static class SubscriptionRowMapper implements RowMapper<Subscription> {
        @Override
        public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subscription subscription = new Subscription();
            subscription.setId(rs.getLong("id"));
            subscription.setStart(rs.getObject("start", LocalDate.class));
            subscription.setEnd(rs.getObject("end", LocalDate.class));
            subscription.setSubscriptionTier(SubscriptionTier.valueOf(rs.getString("subscription_tier")));
            subscription.setSubscriptionDuration(SubscriptionDuration.valueOf(rs.getString("subscription_duration")));
            return subscription;
        }
    }
}

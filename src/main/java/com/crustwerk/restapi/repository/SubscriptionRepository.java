package com.crustwerk.restapi.repository;

import com.crustwerk.restapi.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> getSubscriptionsByStartAfterAndEndBefore(LocalDate start, LocalDate end);

    @Query(value = """
        SELECT * FROM "subscription" s
        WHERE
        (s."start" BETWEEN :start AND :end
          OR s."end" BETWEEN :start AND :end)
        OR
        (:start BETWEEN s."start" AND s."end"
          OR :end BETWEEN s."start" AND s."end")
        """,
    nativeQuery = true)
    List<Subscription> getAllSubscriptionByFilter(@Param("start") LocalDate start, @Param("end") LocalDate end);
}

package com.crustwerk.restapi.dao;

import com.crustwerk.restapi.model.Subscription;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionDao {
    List<Subscription> getSubscriptionsByStartAfterAndEndBefore(LocalDate start, LocalDate end);

    List<Subscription> getAllSubscriptionByFilter(LocalDate start, LocalDate end);

    Subscription getSubscriptionById(Long id);

    void addSubscription(Subscription subscription);
}

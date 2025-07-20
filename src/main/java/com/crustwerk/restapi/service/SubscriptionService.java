package com.crustwerk.restapi.service;

import com.crustwerk.restapi.dao.SubscriptionDao;
import com.crustwerk.restapi.model.Subscription;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionDao subscriptionDao;

    public SubscriptionService(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    // Creazione di una nuova Subscription con la durata basata su un tipo
    public Subscription createSubscription(Subscription subscription) {
        LocalDate start = LocalDate.now();
        LocalDate end = switch (subscription.getSubscriptionDuration()) {
            case MONTHLY -> start.plusMonths(1);
            case YEARLY -> start.plusYears(1);
        };

        subscription.setStart(start);
        subscription.setEnd(end);

        // Salviamo la subscription usando JdbcTemplate nel DAO
        subscriptionDao.addSubscription(subscription);
        return subscription; // Restituiamo la subscription creata
    }

    // Recupera una Subscription per ID
    public Optional<Subscription> getSubscriptionById(Long id) {
        Subscription subscription = subscriptionDao.getSubscriptionById(id);
        return Optional.ofNullable(subscription);
    }

    // Recupera tutte le Subscription tra due date
    public List<Subscription> getSubscriptionBetweenDates(LocalDate start, LocalDate end) {
        return subscriptionDao.getAllSubscriptionByFilter(start, end);
    }
}

package com.crustwerk.restapi.service;

import com.crustwerk.restapi.dao.SubscriptionDaoImpl;
import com.crustwerk.restapi.model.Subscription;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionDaoImpl subscriptionDaoImpl;

    public SubscriptionService(SubscriptionDaoImpl subscriptionDaoImpl) {
        this.subscriptionDaoImpl = subscriptionDaoImpl;
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
        subscriptionDaoImpl.addSubscription(subscription);
        return subscription; // Restituiamo la subscription creata
    }

    // Recupera una Subscription per ID
    public Optional<Subscription> getSubscriptionById(Long id) {
        Subscription subscription = subscriptionDaoImpl.getSubscriptionById(id);
        return Optional.ofNullable(subscription);
    }

    // Recupera tutte le Subscription tra due date
    public List<Subscription> getSubscriptionBetweenDates(LocalDate start, LocalDate end) {
        return subscriptionDaoImpl.getAllSubscriptionByFilter(start, end);
    }
}

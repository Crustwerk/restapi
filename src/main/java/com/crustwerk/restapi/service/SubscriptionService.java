package com.crustwerk.restapi.service;

import com.crustwerk.restapi.model.Subscription;
import com.crustwerk.restapi.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription createSubscription(Subscription subscription) {
        LocalDate start = LocalDate.now();
        LocalDate end = switch (subscription.getSubscriptionDuration()) {
            case MONTHLY -> start.plusMonths(1);
            case YEARLY -> start.plusYears(1);
        };
        subscription.setStart(start);
        subscription.setEnd(end);
        return subscriptionRepository.save(subscription);
    }

    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public List<Subscription> getSubscriptionBetweenDates(LocalDate start, LocalDate end) {
        //return subscriptionRepository.getSubscriptionsByStartAfterAndEndBefore(start, end);
        return subscriptionRepository.getAllSubscriptionByFilter(start, end);

    }
}

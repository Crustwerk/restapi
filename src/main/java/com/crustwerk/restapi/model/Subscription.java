package com.crustwerk.restapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Subscription {
    private LocalDate start;
    private LocalDate end;
    private SubscriptionTier subscriptionTier;
    private SubscriptionDuration subscriptionDuration;
    @Id
    private Long id;

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public SubscriptionTier getSubscriptionTier() {
        return subscriptionTier;
    }

    public void setSubscriptionTier(SubscriptionTier subscriptionTier) {
        this.subscriptionTier = subscriptionTier;
    }

    public SubscriptionDuration getSubscriptionDuration() {
        return subscriptionDuration;
    }

    public void setSubscriptionDuration(SubscriptionDuration subscriptionDuration) {
        this.subscriptionDuration = subscriptionDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

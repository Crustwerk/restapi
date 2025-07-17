package com.crustwerk.restapi.mapper;

import com.crustwerk.restapi.dto.subscription.response.CreateSubscriptionResponse;
import com.crustwerk.restapi.dto.subscription.response.GetSubscriptionResponse;
import com.crustwerk.restapi.model.Subscription;
import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.crustwerk.restapi.dto.subscription.response.CreateSubscriptionResponse.CreateSubscriptionResponseBuilder;
import static com.crustwerk.restapi.dto.subscription.response.GetSubscriptionResponse.GetSubscriptionResponseBuilder;

@Component
public class SubscriptionMapper {

    public Subscription toModel(SubscriptionTier tier, SubscriptionDuration duration) {
        if (tier == null || duration == null) return null;

        Subscription subscription = new Subscription();
        subscription.setSubscriptionTier(tier);
        subscription.setSubscriptionDuration(duration);
        return subscription;
    }

    public Subscription toModel(LocalDate start, LocalDate end) {
        if (start == null || end == null) return null;

        Subscription subscription = new Subscription();
        subscription.setStart(start);
        subscription.setEnd(end);
        return subscription;
    }

    public CreateSubscriptionResponse toCreateSubscriptionResponse(Subscription subscription) {
        if (subscription == null) return null;

        return CreateSubscriptionResponseBuilder
                .aCreateSubscriptionResponse()
                .withId(subscription.getId())
                .withStart(subscription.getStart())
                .withEnd(subscription.getEnd())
                .withSubscriptionTier(subscription.getSubscriptionTier())
                .withSubscriptionDuration(subscription.getSubscriptionDuration())
                .build();
    }

    public GetSubscriptionResponse toGetSubscriptionResponse(Subscription subscription) {
        if (subscription == null) return null;

        return GetSubscriptionResponseBuilder
                .aGetSubscriptionResponse()
                .withEnd(subscription.getEnd())
                .withStart(subscription.getStart())
                .withSubscriptionTier(subscription.getSubscriptionTier())
                .withSubscriptionDuration(subscription.getSubscriptionDuration())
                .build();
    }
}
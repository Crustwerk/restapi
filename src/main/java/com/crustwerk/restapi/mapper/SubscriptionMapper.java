package com.crustwerk.restapi.mapper;

import com.crustwerk.restapi.dto.subscription.request.CreateSubscriptionRequest;
import com.crustwerk.restapi.dto.subscription.request.GetSubscriptionBetweenDatesRequest;
import com.crustwerk.restapi.dto.subscription.response.CreateSubscriptionResponse;
import com.crustwerk.restapi.dto.subscription.response.GetSubscriptionResponse;
import com.crustwerk.restapi.model.Subscription;
import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;
import org.springframework.stereotype.Component;

import static com.crustwerk.restapi.dto.subscription.response.CreateSubscriptionResponse.CreateSubscriptionResponseBuilder;
import static com.crustwerk.restapi.dto.subscription.response.GetSubscriptionResponse.GetSubscriptionResponseBuilder;

@Component
public class SubscriptionMapper {

    public Subscription toModel(CreateSubscriptionRequest req) {
        if (req == null) return null;

        Subscription subscription = new Subscription();
        subscription.setSubscriptionTier(SubscriptionTier.valueOf(req.subscriptionTier()));
        subscription.setSubscriptionDuration(SubscriptionDuration.valueOf(req.subscriptionDuration()));
        return subscription;
    }

    public Subscription toModel(GetSubscriptionBetweenDatesRequest req) {
        if (req == null) return null;

        Subscription subscription = new Subscription();
        subscription.setStart(req.start());
        subscription.setEnd(req.end());
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
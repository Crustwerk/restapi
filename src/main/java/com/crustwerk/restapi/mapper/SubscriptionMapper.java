package com.crustwerk.restapi.mapper;

import com.crustwerk.restapi.dto.subscription.request.CreateSubscriptionRequest;
import com.crustwerk.restapi.dto.subscription.request.GetSubscriptionBetweenDatesRequest;
import com.crustwerk.restapi.dto.subscription.response.CreateSubscriptionResponse;
import com.crustwerk.restapi.dto.subscription.response.CreateSubscriptionResponseBuilder;
import com.crustwerk.restapi.dto.subscription.response.GetSubscriptionResponse;
import com.crustwerk.restapi.model.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public Subscription toModel(CreateSubscriptionRequest createSubscriptionRequest) {
        if (createSubscriptionRequest == null) return null;

        Subscription subscription = new Subscription();
        subscription.setSubscriptionTier(createSubscriptionRequest.getSubscriptionTier());
        subscription.setSubscriptionDuration(createSubscriptionRequest.getSubscriptionDuration());
        return subscription;
    }

    public Subscription toModel(GetSubscriptionBetweenDatesRequest req) {
        if (req == null) return null;

        Subscription subscription = new Subscription();
        subscription.setStart(req.getStart());
        subscription.setEnd(req.getEnd());
        return subscription;
    }


    public CreateSubscriptionResponse toCreateSubscriptionResponse(Subscription subscription) {
        if (subscription == null) return null;

        return CreateSubscriptionResponseBuilder
                .aCreateSubscriptionResponse()
                .id(subscription.getId())
                .start(subscription.getStart())
                .end(subscription.getEnd())
                .subscriptionTier(subscription.getSubscriptionTier())
                .subscriptionDuration(subscription.getSubscriptionDuration())
                .build();
    }

    public GetSubscriptionResponse toGetSubscriptionResponse(Subscription subscription) {
        if (subscription == null) return null;

        GetSubscriptionResponse response = new GetSubscriptionResponse();
        response.setEnd(subscription.getEnd());
        response.setStart(subscription.getStart());
        response.setSubscriptionTier(subscription.getSubscriptionTier());
        response.setSubscriptionDuration(subscription.getSubscriptionDuration());
        return response;
    }


}
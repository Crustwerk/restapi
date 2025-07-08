package com.crustwerk.restapi.dto.subscription.request;

import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;

public record CreateSubscriptionRequest(SubscriptionTier subscriptionTier, SubscriptionDuration subscriptionDuration) {

    public static final class CreateSubscriptionRequestBuilder {
        private SubscriptionTier subscriptionTier;
        private SubscriptionDuration subscriptionDuration;

        private CreateSubscriptionRequestBuilder() {
        }

        public static CreateSubscriptionRequestBuilder aCreateSubscriptionRequest() {
            return new CreateSubscriptionRequestBuilder();
        }

        public CreateSubscriptionRequestBuilder withSubscriptionTier(SubscriptionTier subscriptionTier) {
            this.subscriptionTier = subscriptionTier;
            return this;
        }

        public CreateSubscriptionRequestBuilder withSubscriptionDuration(SubscriptionDuration subscriptionDuration) {
            this.subscriptionDuration = subscriptionDuration;
            return this;
        }

        public CreateSubscriptionRequest build() {
            return new CreateSubscriptionRequest(subscriptionTier, subscriptionDuration);
        }
    }
}

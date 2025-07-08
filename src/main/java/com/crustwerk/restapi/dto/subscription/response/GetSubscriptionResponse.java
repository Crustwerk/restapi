package com.crustwerk.restapi.dto.subscription.response;

import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;

import java.time.LocalDate;

public record GetSubscriptionResponse(LocalDate start, LocalDate end, SubscriptionTier subscriptionTier,
                                      SubscriptionDuration subscriptionDuration) {


    public static final class GetSubscriptionResponseBuilder {
        private LocalDate start;
        private LocalDate end;
        private SubscriptionTier subscriptionTier;
        private SubscriptionDuration subscriptionDuration;

        private GetSubscriptionResponseBuilder() {
        }

        public static GetSubscriptionResponseBuilder aGetSubscriptionResponse() {
            return new GetSubscriptionResponseBuilder();
        }

        public GetSubscriptionResponseBuilder withStart(LocalDate start) {
            this.start = start;
            return this;
        }

        public GetSubscriptionResponseBuilder withEnd(LocalDate end) {
            this.end = end;
            return this;
        }

        public GetSubscriptionResponseBuilder withSubscriptionTier(SubscriptionTier subscriptionTier) {
            this.subscriptionTier = subscriptionTier;
            return this;
        }

        public GetSubscriptionResponseBuilder withSubscriptionDuration(SubscriptionDuration subscriptionDuration) {
            this.subscriptionDuration = subscriptionDuration;
            return this;
        }

        public GetSubscriptionResponse build() {
            return new GetSubscriptionResponse(start, end, subscriptionTier, subscriptionDuration);
        }
    }
}

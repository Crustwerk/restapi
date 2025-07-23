package com.crustwerk.restapi.dto.subscription.response;

import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;

import java.time.LocalDate;


public record CreateSubscriptionResponse(
        Long id,
        LocalDate start,
        LocalDate end,
        SubscriptionTier subscriptionTier,
        SubscriptionDuration subscriptionDuration
) {

    public static final class CreateSubscriptionResponseBuilder {
        private Long id;
        private LocalDate start;
        private LocalDate end;
        private SubscriptionTier subscriptionTier;
        private SubscriptionDuration subscriptionDuration;

        private CreateSubscriptionResponseBuilder() {
        }

        public static CreateSubscriptionResponseBuilder aCreateSubscriptionResponse() {
            return new CreateSubscriptionResponseBuilder();
        }

        public CreateSubscriptionResponseBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CreateSubscriptionResponseBuilder withStart(LocalDate start) {
            this.start = start;
            return this;
        }

        public CreateSubscriptionResponseBuilder withEnd(LocalDate end) {
            this.end = end;
            return this;
        }

        public CreateSubscriptionResponseBuilder withSubscriptionTier(SubscriptionTier subscriptionTier) {
            this.subscriptionTier = subscriptionTier;
            return this;
        }

        public CreateSubscriptionResponseBuilder withSubscriptionDuration(SubscriptionDuration subscriptionDuration) {
            this.subscriptionDuration = subscriptionDuration;
            return this;
        }

        public CreateSubscriptionResponse build() {
            return new CreateSubscriptionResponse(id, start, end, subscriptionTier, subscriptionDuration);
        }
    }
}


package com.crustwerk.restapi.dto.subscription.request;

import java.time.LocalDate;

public record GetSubscriptionBetweenDatesRequest(LocalDate start, LocalDate end) {

    public static final class GetSubscriptionBetweenDatesRequestBuilder {
        private LocalDate start;
        private LocalDate end;

        private GetSubscriptionBetweenDatesRequestBuilder() {
        }

        public static GetSubscriptionBetweenDatesRequestBuilder aGetSubscriptionBetweenDatesRequest() {
            return new GetSubscriptionBetweenDatesRequestBuilder();
        }

        public GetSubscriptionBetweenDatesRequestBuilder withStart(LocalDate start) {
            this.start = start;
            return this;
        }

        public GetSubscriptionBetweenDatesRequestBuilder withEnd(LocalDate end) {
            this.end = end;
            return this;
        }

        public GetSubscriptionBetweenDatesRequest build() {
            return new GetSubscriptionBetweenDatesRequest(start, end);
        }
    }
}

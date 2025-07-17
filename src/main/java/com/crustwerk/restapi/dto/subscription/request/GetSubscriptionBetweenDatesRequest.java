package com.crustwerk.restapi.dto.subscription.request;

import com.crustwerk.restapi.validation.ValidDate;
import jakarta.validation.constraints.NotBlank;

public record GetSubscriptionBetweenDatesRequest(

        @NotBlank(message = "Start date is required")
        @ValidDate
        String start,

        @NotBlank(message = "End date is required")
        @ValidDate
        String end
) {

    public static final class GetSubscriptionBetweenDatesRequestBuilder {

        private String start;
        private String end;

        private GetSubscriptionBetweenDatesRequestBuilder() {
        }

        public static GetSubscriptionBetweenDatesRequestBuilder aGetSubscriptionBetweenDatesRequest() {
            return new GetSubscriptionBetweenDatesRequestBuilder();
        }

        public GetSubscriptionBetweenDatesRequestBuilder withStart(String start) {
            this.start = start;
            return this;
        }

        public GetSubscriptionBetweenDatesRequestBuilder withEnd(String end) {
            this.end = end;
            return this;
        }

        public GetSubscriptionBetweenDatesRequest build() {
            return new GetSubscriptionBetweenDatesRequest(start, end);
        }
    }
}

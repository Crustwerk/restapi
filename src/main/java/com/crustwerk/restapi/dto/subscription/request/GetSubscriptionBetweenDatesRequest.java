package com.crustwerk.restapi.dto.subscription.request;

import com.crustwerk.restapi.validation.ValidDateRange;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@ValidDateRange
public record GetSubscriptionBetweenDatesRequest(
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Start date is required")
        LocalDate start,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "End date is required")
        LocalDate end
) {

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

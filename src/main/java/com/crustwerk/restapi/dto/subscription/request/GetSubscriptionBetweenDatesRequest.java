package com.crustwerk.restapi.dto.subscription.request;

import com.crustwerk.restapi.validation.ValidDate;
import com.crustwerk.restapi.validation.ValidDateGroup;
import com.crustwerk.restapi.validation.ValidDateRange;
import com.crustwerk.restapi.validation.ValidDateRangeGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;


@ValidDateRange(groups = ValidDateRangeGroup.class)
@GroupSequence({GetSubscriptionBetweenDatesRequest.class, ValidDateGroup.class, ValidDateRangeGroup.class})
public record GetSubscriptionBetweenDatesRequest(
        @NotBlank(message = "Start date is required")
        @ValidDate(groups = ValidDateGroup.class)
        String start,

        @NotBlank(message = "End date is required")
        @ValidDate(groups = ValidDateGroup.class)
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

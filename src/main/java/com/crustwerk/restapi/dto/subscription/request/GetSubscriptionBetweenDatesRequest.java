package com.crustwerk.restapi.dto.subscription.request;

import com.crustwerk.restapi.validation.ValidDate;
import com.crustwerk.restapi.validation.ValidDateInterface;
import com.crustwerk.restapi.validation.ValidDateRange;
import com.crustwerk.restapi.validation.ValidDateRangeInterface;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;


@ValidDateRange(groups = ValidDateRangeInterface.class)
@GroupSequence({GetSubscriptionBetweenDatesRequest.class, ValidDateInterface.class, ValidDateRangeInterface.class})
public record GetSubscriptionBetweenDatesRequest(

        @NotBlank(message = "Start date is required")
        @ValidDate(groups = ValidDateInterface.class)
        String start,

        @NotBlank(message = "End date is required")
        @ValidDate(groups = ValidDateInterface.class)
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

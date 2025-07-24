package com.crustwerk.restapi.dto.subscription.request;

import com.crustwerk.restapi.validation.ValidDate;
import com.crustwerk.restapi.validation.ValidDateRange;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;

/**
 * <b>Bean Validation standard (JSR-303/380)</b> il gruppo di default è Default.class
 * <br>
 *
 * <b>Spring's Bean Validation integration</b> quando usi @GroupSequence su una classe, quella classe diventa il gruppo di default per le sue validazioni
 */
@ValidDateRange(groups = {ValidDateRange.Group.class})
@GroupSequence({GetSubscriptionBetweenDatesRequest.class, ValidDate.Group.class, ValidDateRange.Group.class})
public record GetSubscriptionBetweenDatesRequest(
        @NotBlank(message = "Start date is required")
        @ValidDate(groups = ValidDate.Group.class)
        String start,

        @NotBlank(message = "End date is required")
        @ValidDate(groups = ValidDate.Group.class)
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

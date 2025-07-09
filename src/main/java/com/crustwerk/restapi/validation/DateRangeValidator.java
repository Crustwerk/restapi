package com.crustwerk.restapi.validation;

import com.crustwerk.restapi.dto.subscription.request.GetSubscriptionBetweenDatesRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange,
        GetSubscriptionBetweenDatesRequest> {

    @Override
    public boolean isValid(GetSubscriptionBetweenDatesRequest request,
                           ConstraintValidatorContext context) {
        if (request.start() == null || request.end() == null) return true; // delega a @NotNull

        return request.start().isBefore(request.end());
    }
}

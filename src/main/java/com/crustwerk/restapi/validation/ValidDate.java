package com.crustwerk.restapi.validation;

import com.crustwerk.restapi.Utils;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Documented
@Constraint(validatedBy = ValidDate.ValidDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {

    String message() default "Date must be in format: " + Utils.DATE_TIME_PATTERN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    interface Group {
    }

    class ValidDateValidator implements ConstraintValidator<ValidDate, String> {
        @Override
        public void initialize(ValidDate constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }

            try {
                LocalDate.parse(value, Utils.DATE_TIME_FORMATTER);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
    }
}

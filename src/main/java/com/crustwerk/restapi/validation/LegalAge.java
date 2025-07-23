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
@Constraint(validatedBy = LegalAge.LegalAgeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LegalAge {

    String message() default "User must be of legal age";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class LegalAgeValidator implements ConstraintValidator<LegalAge, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }

            try {
                LocalDate dateOfBirth = LocalDate.parse(value, Utils.DATE_TIME_FORMATTER);
                return dateOfBirth.isBefore(LocalDate.now().minusYears(18));
            } catch (DateTimeParseException ex) {

                //in caso di errore di parse delego tutto a @ValidDate
                return true;
            }
        }
    }
}

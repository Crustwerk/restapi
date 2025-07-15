package com.crustwerk.restapi.validation;

import com.crustwerk.restapi.dto.user.request.CreateUserRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;

@Documented
@Constraint(validatedBy = UnderageUser.UnderageUserValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnderageUser {

    String message() default "The user must be of legal age";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UnderageUserValidator implements ConstraintValidator<UnderageUser, CreateUserRequest> {

        @Override
        public boolean isValid(CreateUserRequest value, ConstraintValidatorContext context) {
            if (value == null || value.dateOfBirth() == null) return true;
            return value.dateOfBirth().isBefore(LocalDate.now().minusYears(18));
        }
    }
}

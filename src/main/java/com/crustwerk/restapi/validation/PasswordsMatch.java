package com.crustwerk.restapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;

@Documented
@Constraint(validatedBy = PasswordsMatch.PasswordsMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {

    String message() default "Passwords do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            try {
                String password = (String) value.getClass().getMethod("password").invoke(value);
                String confirmPassword = (String) value.getClass().getMethod("confirmPassword").invoke(value);
                if (password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
                    return true;
                }
                return password.equals(confirmPassword);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Error accessing password fields")
                        .addConstraintViolation();

                return false;
            }
        }
    }
}
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

    interface Group {
    }

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
                boolean matches = password.equals(confirmPassword);
                if (!matches){
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Passwords do not match")
                            .addPropertyNode("confirmPassword")
                            .addConstraintViolation();

                }

                return matches;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Error accessing password fields")
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation();

                return false;
            }
        }
    }
}
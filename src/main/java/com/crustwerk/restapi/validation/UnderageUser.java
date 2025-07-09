package com.crustwerk.restapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnderageUserValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnderageUser {

    String message() default "The user must be of legal age";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.crustwerk.restapi.validation;

import com.crustwerk.restapi.dto.user.request.CreateUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class UnderageUserValidator implements ConstraintValidator<UnderageUser, CreateUserRequest> {

    @Override
    public boolean isValid(CreateUserRequest value, ConstraintValidatorContext context) {
        if (value == null || value.dateOfBirth() == null) return true;
        return value.dateOfBirth().isBefore(LocalDate.now().minusYears(18));
    }
}

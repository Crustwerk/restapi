package com.crustwerk.restapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidEnum.ValidEnumValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    String message() default "Invalid enum value.";

    Class<? extends Enum<?>> enumClass();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {
        private Enum<?>[] enumConstants;
        private String validValuesMessage;

        @Override
        public void initialize(ValidEnum annotation) {
            // Ottengo i valori dell'Enum dalla classe passata nell'annotazione
            this.enumConstants = annotation.enumClass().getEnumConstants();

            // Costruisco il messaggio dinamico con i valori dell'Enum
            StringBuilder validValues = new StringBuilder();
            for (Enum<?> enumConstant : enumConstants) {
                validValues.append(enumConstant.name()).append(", ");
            }

            // Rimuovo l'ultima virgola e spazio
            if (!validValues.isEmpty()) {
                validValues.setLength(validValues.length() - 2);
            }

            validValuesMessage = validValues.toString();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }

            // Controllo se la stringa corrisponde a un valore dell'Enum
            for (Enum<?> enumConstant : enumConstants) {
                if (enumConstant.name().equals(value)) {
                    return true;
                }
            }

            // Se non è valido, imposto il messaggio dinamico
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid value '" + value + "'. Valid values are: " + validValuesMessage)
                    .addConstraintViolation();

            return false;  // Valore non valido
        }
    }

}

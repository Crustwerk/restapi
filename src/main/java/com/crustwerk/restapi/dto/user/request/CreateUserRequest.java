package com.crustwerk.restapi.dto.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateUserRequest(

        @NotBlank(message = "Username is required")
        String username,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Confirm password is required")
        String confirmPassword,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        //inserire qui validatore custom
        LocalDate dateOfBirth) {

    public static final class CreateUserRequestBuilder {
        private String username;
        private String email;
        private String password;
        private String confirmPassword;
        private LocalDate dateOfBirth;

        private CreateUserRequestBuilder() {
        }

        public static CreateUserRequestBuilder aCreateUserRequest() {
            return new CreateUserRequestBuilder();
        }

        public CreateUserRequestBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public CreateUserRequestBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CreateUserRequestBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public CreateUserRequestBuilder withConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public CreateUserRequestBuilder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(username, email, password, confirmPassword, dateOfBirth);
        }
    }
}

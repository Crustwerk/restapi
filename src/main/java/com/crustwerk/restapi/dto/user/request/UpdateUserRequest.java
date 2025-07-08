package com.crustwerk.restapi.dto.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UpdateUserRequest(

        @NotBlank(message = "Username is required")
        String username,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Confirm password is required")
        String confirmPassword,

        String currentPassword,

        String newPassword,

        String confirmNewPassword
) {
    public static final class UpdateUserRequestBuilder {
        private String username;
        private String email;
        private LocalDate dateOfBirth;
        private String password;
        private String confirmPassword;
        private String currentPassword;
        private String newPassword;
        private String confirmNewPassword;

        private UpdateUserRequestBuilder() {
        }

        public static UpdateUserRequestBuilder anUpdateUserRequest() {
            return new UpdateUserRequestBuilder();
        }

        public UpdateUserRequestBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UpdateUserRequestBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UpdateUserRequestBuilder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public UpdateUserRequestBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UpdateUserRequestBuilder withConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public UpdateUserRequestBuilder withCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
            return this;
        }

        public UpdateUserRequestBuilder withNewPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public UpdateUserRequestBuilder withConfirmNewPassword(String confirmNewPassword) {
            this.confirmNewPassword = confirmNewPassword;
            return this;
        }

        public UpdateUserRequest build() {
            return new UpdateUserRequest(username, email, dateOfBirth, password, confirmPassword, currentPassword, newPassword, confirmNewPassword);
        }
    }
}
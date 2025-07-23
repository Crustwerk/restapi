package com.crustwerk.restapi.dto.user.request;

import com.crustwerk.restapi.validation.PasswordsMatch;
import jakarta.validation.constraints.NotBlank;

@PasswordsMatch
public record DeleteUserRequest(
        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Confirm password is required")
        String confirmPassword
) {
    public static final class DeleteUserRequestBuilder {
        private String password;
        private String confirmPassword;

        private DeleteUserRequestBuilder() {
        }

        public static DeleteUserRequestBuilder aDeleteUserRequest() {
            return new DeleteUserRequestBuilder();
        }

        public DeleteUserRequestBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public DeleteUserRequestBuilder withConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public DeleteUserRequest build() {
            return new DeleteUserRequest(password, confirmPassword);
        }
    }
}

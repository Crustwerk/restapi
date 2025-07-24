package com.crustwerk.restapi.dto.user.request;

import com.crustwerk.restapi.validation.*;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@PasswordsMatch(groups = PasswordsMatch.Group.class)
@GroupSequence({CreateUserRequest.class, PasswordsMatch.Group.class, ValidDate.Group.class, LegalAge.Group.class})
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

        @LegalAge(groups = LegalAge.Group.class)
        @ValidDate(groups = ValidDate.Group.class)
        @NotBlank(message = "Date of birth is required")
        String dateOfBirth
) {

    public static final class CreateUserRequestBuilder {
        private String username;
        private String email;
        private String password;
        private String confirmPassword;
        private String dateOfBirth;

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

        public CreateUserRequestBuilder withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(username, email, password, confirmPassword, dateOfBirth);
        }
    }
}

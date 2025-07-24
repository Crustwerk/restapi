package com.crustwerk.restapi.dto.user.request;

import com.crustwerk.restapi.validation.LegalAge;
import com.crustwerk.restapi.validation.PasswordsMatch;
import com.crustwerk.restapi.validation.ValidDate;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@PasswordsMatch(groups = PasswordsMatch.Group.class)
@GroupSequence({UpdateUserRequest.class, PasswordsMatch.Group.class, ValidDate.Group.class, LegalAge.Group.class})
public record UpdateUserRequest(
        @NotBlank(message = "Username is required")
        String username,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,

        @LegalAge(groups = LegalAge.Group.class)
        @ValidDate(groups = ValidDate.Group.class)
        @NotBlank(message = "Date of birth is required")
        String dateOfBirth,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Confirm password is required")
        String confirmPassword
) {
    public static final class UpdateUserRequestBuilder {
        private String username;
        private String email;
        private String dateOfBirth;
        private String password;
        private String confirmPassword;

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

        public UpdateUserRequestBuilder withDateOfBirth(String dateOfBirth) {
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


        public UpdateUserRequest build() {
            return new UpdateUserRequest(username, email, dateOfBirth, password, confirmPassword);
        }
    }
}
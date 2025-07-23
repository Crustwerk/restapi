package com.crustwerk.restapi.dto.user.response;

import java.time.LocalDate;


public record CreateUserResponse(
        Long id,

        String username,

        String email,

        LocalDate dateOfBirth
) {
    public static final class CreateUserResponseBuilder {
        private Long id;
        private String username;
        private String email;
        private LocalDate dateOfBirth;

        private CreateUserResponseBuilder() {
        }

        public static CreateUserResponseBuilder aCreateUserResponse() {
            return new CreateUserResponseBuilder();
        }

        public CreateUserResponseBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CreateUserResponseBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public CreateUserResponseBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CreateUserResponseBuilder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public CreateUserResponse build() {
            return new CreateUserResponse(id, username, email, dateOfBirth);
        }
    }
}

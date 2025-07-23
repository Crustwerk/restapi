package com.crustwerk.restapi.dto.user.response;

import java.time.LocalDate;

public record GetUserResponse(
        Long id,

        String username,

        String email,

        LocalDate dateOfBirth
) {
    public static final class GetUserResponseBuilder {
        private Long id;
        private String username;
        private String email;
        private LocalDate dateOfBirth;

        private GetUserResponseBuilder() {
        }

        public static GetUserResponseBuilder aGetUserResponse() {
            return new GetUserResponseBuilder();
        }

        public GetUserResponseBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public GetUserResponseBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public GetUserResponseBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public GetUserResponseBuilder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public GetUserResponse build() {
            return new GetUserResponse(id, username, email, dateOfBirth);
        }
    }
}

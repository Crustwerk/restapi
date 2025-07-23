CREATE TABLE IF NOT EXISTS "user"
(
    "id"             BIGINT AUTO_INCREMENT PRIMARY KEY,
    "username"       VARCHAR(255) NOT NULL,
    "email"          VARCHAR(255) NOT NULL UNIQUE,
    "password_hash"  VARCHAR(255) NOT NULL,
    "date_of_birth"  DATE,
    "created_at"     DATE,
    "last_update_at" DATE
);

CREATE TABLE IF NOT EXISTS "subscription"
(
    "id"                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    "start"                 DATE         NOT NULL,
    "end"                   DATE         NOT NULL,
    "subscription_tier"     VARCHAR(255) NOT NULL,
    "subscription_duration" VARCHAR(255) NOT NULL
);
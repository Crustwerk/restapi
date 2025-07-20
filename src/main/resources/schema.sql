-- schema.sql

-- Creazione della tabella "user"
CREATE TABLE IF NOT EXISTS "user"
(
    "id"           BIGINT AUTO_INCREMENT PRIMARY KEY,
    "username"     VARCHAR(255) NOT NULL,
    "email"        VARCHAR(255) NOT NULL UNIQUE,
    "passwordHash" VARCHAR(255) NOT NULL,
    "dateOfBirth"  DATE,
    "createdAt"    DATE,
    "lastUpdateAt" DATE
);

-- Creazione della tabella "subscription"
CREATE TABLE IF NOT EXISTS "subscription"
(
    "id"                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    "start"                DATE         NOT NULL,
    "end"                  DATE         NOT NULL,
    "subscriptionTier"     VARCHAR(255) NOT NULL,
    "subscriptionDuration" VARCHAR(255) NOT NULL
);

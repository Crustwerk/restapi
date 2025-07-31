-- User
INSERT INTO "user" ("username", "email", "password_hash", "date_of_birth", "created_at", "last_update_at")
VALUES ('PippoLaColla', 'pippola.colla@libero.it', 'hashedpassword', '2007-06-30', '2025-01-01', '2025-01-01');

INSERT INTO "user" ("username", "email", "password_hash", "date_of_birth", "created_at", "last_update_at")
VALUES ('GiovanniRossi', 'giovanni.rossi@example.com', 'hashedpassword123', '1990-05-15', '2025-01-01', '2025-01-01');

-- Subscription
INSERT INTO "subscription" ("start", "end", "subscription_tier", "subscription_duration")
VALUES ('2025-01-01', '2025-02-01', 'FREE', 'MONTHLY');

INSERT INTO "subscription" ("start", "end", "subscription_tier", "subscription_duration")
VALUES ('2025-01-01', '2025-12-31', 'PREMIUM', 'YEARLY');

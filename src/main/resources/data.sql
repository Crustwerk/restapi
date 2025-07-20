-- data.sql

-- Inserimento di un utente di esempio
INSERT INTO "user" ("username", "email", "passwordHash", "dateOfBirth", "createdAt", "lastUpdateAt")
VALUES ('PippoLaColla', 'pippola.colla@libero.it', 'hashedpassword', '2007-06-30', '2025-01-01', '2025-01-01');

-- Inserimento di un altro utente di esempio
INSERT INTO "user" ("username", "email", "passwordHash", "dateOfBirth", "createdAt", "lastUpdateAt")
VALUES ('GiovanniRossi', 'giovanni.rossi@example.com', 'hashedpassword123', '1990-05-15', '2025-01-01', '2025-01-01');

-- Inserimento di una subscription di esempio
INSERT INTO "subscription" ("start", "end", "subscriptionTier", "subscriptionDuration")
VALUES ('2025-01-01', '2025-02-01', 'BASIC', 'MONTHLY');

-- Inserimento di una seconda subscription di esempio
INSERT INTO "subscription" ("start", "end", "subscriptionTier", "subscriptionDuration")
VALUES ('2025-01-01', '2025-12-31', 'PREMIUM', 'YEARLY');

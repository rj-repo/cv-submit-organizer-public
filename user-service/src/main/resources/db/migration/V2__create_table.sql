CREATE TABLE IF NOT EXISTS profiles.users
(
    id         SERIAL PRIMARY KEY,
    email   VARCHAR(255) NOT NULL UNIQUE,
    firstname varchar(255) NOT NULL ,
    surname varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS profiles.user_history
(
    id         SERIAL PRIMARY KEY,
    user_id   BIGINT not null,
    previous_email varchar not null,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES profiles.users(id)
);


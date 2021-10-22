DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id        BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    lastname  VARCHAR(30),
    firstname VARCHAR(30)
);


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id   UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    avatar VARCHAR(255),
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
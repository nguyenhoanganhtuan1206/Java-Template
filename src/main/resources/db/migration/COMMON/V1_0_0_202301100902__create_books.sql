CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE books
(
    id   UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    image VARCHAR(255),
    user_id UUID NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO books (name, author, user_id)
VALUES
    ('The Lord of the Rings', 'Anh Tuan', '961d553a-b8dd-11ed-afa1-0242ac120002'),
    ('Harry Potter', 'Anh Tuan', '961d553a-b8dd-11ed-afa1-0242ac120002'),
    ('Three Kingdoms', 'Anh Tuan', 'be00f232-b8dd-11ed-afa1-0242ac120002');

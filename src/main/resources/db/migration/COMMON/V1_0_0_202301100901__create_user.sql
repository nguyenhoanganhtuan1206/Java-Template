CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id        UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    username  VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    enabled   BOOLEAN      NOT NULL,
    avatar    VARCHAR(255),
    role_id   UUID         NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users (id, username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('39ee4a9e-35d9-4c4b-8272-7b3132598c74', 'admin', 'vhga65tQgga+rlGKUKAxTJEmnDqOePeQ+0+Y3vOgLwo=', 'Nguyen H',
        'A Tuan', true, null, (select id from roles where name = 'ADMIN')),
       ('961d553a-b8dd-11ed-afa1-0242ac120002', 'nguyenhanhtuan1206', 'OHjcWdaFE35A7kBJBmVyjlNfaJ8lWgHI2zaUR4tjGhI=',
        'Nguyen', 'Tuan', true, null, (SELECT id from roles where name = 'CONTRIBUTOR')),
       ('be00f232-b8dd-11ed-afa1-0242ac120002', 'anhtuan2001', 'OHjcWdaFE35A7kBJBmVyjlNfaJ8lWgHI2zaUR4tjGhI=', 'Nguyen',
        'A Tuan', true, null, (SELECT id from roles where name = 'CONTRIBUTOR'));

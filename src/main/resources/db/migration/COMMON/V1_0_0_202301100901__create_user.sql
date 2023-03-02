CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id   UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    avatar VARCHAR(255),
    role_id UUID NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO users (username, password, firstName, lastName, enabled, avatar, role_id)
VALUES
    ('admin', 'vhga65tQgga+rlGKUKAxTJEmnDqOePeQ+0+Y3vOgLwo=', 'Nguyen H', 'A Tuan', true,null, (select id from roles where name = 'ADMIN')),
    ('nguyenhanhtuan1206', 'OHjcWdaFE35A7kBJBmVyjlNfaJ8lWgHI2zaUR4tjGhI=', 'Nguyen', 'Tuan', true, null, (SELECT id from roles where name = 'CONTRIBUTOR')),
    ('anhtuan2001', 'OHjcWdaFE35A7kBJBmVyjlNfaJ8lWgHI2zaUR4tjGhI=',  'Nguyen', 'A Tuan', true, null, (SELECT id from roles where name = 'CONTRIBUTOR'));
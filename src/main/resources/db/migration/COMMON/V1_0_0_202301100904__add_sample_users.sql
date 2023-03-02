INSERT INTO users (id, username, password, firstName, lastName, enabled, avatar, role_id)
VALUES
    (1, 'admin', 'vhga65tQgga+rlGKUKAxTJEmnDqOePeQ+0+Y3vOgLwo=', 'Nguyen H', 'A Tuan', true,null, (SELECT id from roles where name = "ADMIN")),
    (2, 'nguyenhanhtuan1206', 'OHjcWdaFE35A7kBJBmVyjlNfaJ8lWgHI2zaUR4tjGhI=', 'Nguyen', 'Tuan', true, null, (SELECT id from roles where name = "CONTRIBUTOR")),
    (3, 'anhtuan2001', 'OHjcWdaFE35A7kBJBmVyjlNfaJ8lWgHI2zaUR4tjGhI=',  'Nguyen', 'A Tuan', true, null, (SELECT id from roles where name = "CONTRIBUTOR"));
INSERT INTO users (id, username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('39ee4a9e-35d9-4c4b-8272-7b3132598c73', 'admin', '$2y$10$2pxndtBcaFFwGdLO8EoC9O.s1HXGuSJiuwk1IgT.CyXVIcJgpKnDO',
        'Nguyen H',
        'A Tuan', true, null, (select id from roles where name = 'ADMIN')),
       ('961d553a-b8dd-11ed-afa1-0242ac120004', 'nguyenhanhtuan1206123',
        '$2y$10$2pxndtBcaFFwGdLO8EoC9O.s1HXGuSJiuwk1IgT.CyXVIcJgpKnDO',
        'Nguyen', 'Tuan', true, null, (SELECT id from roles where name = 'CONTRIBUTOR'));

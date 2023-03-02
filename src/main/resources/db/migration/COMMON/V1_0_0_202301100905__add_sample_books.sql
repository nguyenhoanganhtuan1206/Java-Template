INSERT INTO books (name, author, user_id)
VALUES 
    ('The Lord of the Rings', 'Anh Tuan', (SELECT id FROM users where username="nguyenhanhtuan1206" )),
    ('Harry Potter', 'Anh Tuan', (SELECT id FROM users where username="nguyenhanhtuan1206" )),
    ('Three Kingdoms', 'Anh Tuan', (SELECT id FROM users where username="anhtuan2001" ));
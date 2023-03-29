ALTER TABLE users
    ADD CONSTRAINT unique_username UNIQUE (username);

UPDATE users
SET username = 'nguyenhoanganhtuan1206@gmail.com'
WHERE id = '961d553a-b8dd-11ed-afa1-0242ac120004';


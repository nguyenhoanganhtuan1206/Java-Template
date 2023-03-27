ALTER TABLE users
    ADD COLUMN email VARCHAR(255);

UPDATE users
SET email = 'nguyenhoanganhtuan1206@gmail.com'
WHERE id = '961d553a-b8dd-11ed-afa1-0242ac120004';

ALTER TABLE books
    ADD COLUMN subtitle VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN publisher VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN isbn13 VARCHAR(13),
    ADD COLUMN price VARCHAR(20),
    ADD COLUMN year Integer CHECK (year >= 0),
    ADD COLUMN rating NUMERIC(2,1) DEFAULT 0 CHECK (rating >= 0 AND rating <= 5);

ALTER TABLE books
    ADD CONSTRAINT unique_isbn13 UNIQUE (isbn13);

ALTER TABLE books
ALTER
COLUMN description TYPE TEXT;

UPDATE books
SET subtitle  = 'The Philosopher Stone',
    publisher = 'Bloomsbury Publishing',
    isbn13    = '1231231231231',
    price     = '$22.99',
    year      = 1997,
    rating    = 4
WHERE name = 'The Lord of the Rings';

UPDATE books
SET subtitle  = 'The Sorcerer Stone',
    publisher = 'Scholastic',
    isbn13    = '1234567891234',
    price     = '$16.99',
    year      = 1998,
    rating    = 5
WHERE name = 'Harry Potter';

UPDATE books
SET subtitle  = 'The Sorcerer Stone',
    publisher = 'Scholastic',
    isbn13    = '1234567891236',
    price     = '$16.99',
    year      = 1998,
    rating    = 4.5
WHERE name = 'Three Kingdoms';

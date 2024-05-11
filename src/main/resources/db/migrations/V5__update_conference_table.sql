ALTER TABLE conferences
    DROP COLUMN data,
    ADD COLUMN creation_date DATE NOT NULL,
    ADD COLUMN end_date DATE NOT NULL;

CREATE TABLE if not exists conferences(
    id SERIAL PRIMARY KEY,
    title VARCHAR(30) NOT NULL,
    details VARCHAR(255),
    data DATE NOT NULL,
    created_by_id INTEGER REFERENCES users(id)
);

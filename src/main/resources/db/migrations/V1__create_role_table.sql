CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    authority VARCHAR(15) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255)
);
CREATE TABLE if not exists conferences(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    details VARCHAR(255),
    creation_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_by_id INTEGER REFERENCES users(id)
);
CREATE TABLE user_conference (
    user_id INTEGER NOT NULL,
    conference_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, conference_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (conference_id) REFERENCES conferences(id)
);
CREATE TABLE users_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE user_conference (
    user_id INTEGER NOT NULL,
    conference_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, conference_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (conference_id) REFERENCES conferences(id)
);
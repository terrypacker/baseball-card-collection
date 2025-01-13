--
-- System settings
CREATE TABLE baseballCard(
    id SERIAL,
    player VARCHAR(100) NOT NULL,
    team VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    cardNumber INT,
    year INT,
    notes TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE baseballCardValue (
    baseballCardId INT NOT NULL,
    grade VARCHAR(255),
    value BIGINT,
    time TIMESTAMP,
    CONSTRAINT baseballCardValueFk1 FOREIGN KEY (baseballCardId) REFERENCES baseballCard(id) ON DELETE CASCADE
);

CREATE TABLE applicationUser(
    id SERIAL,
    username VARCHAR(20) UNIQUE,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE role(
    userId INT NOT NULL,
    role VARCHAR(20) NOT NULL,
    CONSTRAINT roleFk1 FOREIGN KEY (userId) REFERENCES applicationUser(id) ON DELETE CASCADE
);

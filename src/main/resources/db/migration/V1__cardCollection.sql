CREATE TABLE baseballCard(
    id SERIAL,
    sport VARCHAR(255) NOT NULL,
    player VARCHAR(100) NOT NULL,
    team VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    cardNumber INT,
    year INT,
    notes TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE ownedCard(
     id SERIAL,
     baseballCardId INT NOT NULL,
     cardIdentifier VARCHAR(255),
     lot VARCHAR(255),
     notes TEXT,
     PRIMARY KEY (id),
     CONSTRAINT ownedCardFk1 FOREIGN KEY (baseballCardId) references baseballCard(id) ON DELETE CASCADE
);

CREATE TABLE ownedCardValue (
    ownedCardId INT NOT NULL,
    grade DOUBLE PRECISION,
    value BIGINT,
    time TIMESTAMP,
    CONSTRAINT ownedCardValueFk1 FOREIGN KEY (ownedCardId) REFERENCES ownedCard(id) ON DELETE CASCADE,
    CONSTRAINT ownedCardValueUnique UNIQUE (ownedCardId, time)
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

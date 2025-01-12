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
    value BIGINT,
    time TIMESTAMP
);
ALTER TABLE baseballCardValue ADD CONSTRAINT baseballCardValuesFk1 FOREIGN KEY (baseballCardId) REFERENCES baseballCard(id) ON DELETE CASCADE;

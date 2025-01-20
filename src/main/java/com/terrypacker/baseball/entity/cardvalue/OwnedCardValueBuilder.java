package com.terrypacker.baseball.entity.cardvalue;

import java.time.ZonedDateTime;

public class OwnedCardValueBuilder {

    private Integer ownedCardId;
    private Double grade;
    private long valueInCents;
    private ZonedDateTime timestamp;

    public static OwnedCardValueBuilder get() {return new OwnedCardValueBuilder();}


    public OwnedCardValueBuilder setOwnedCardId(Integer ownedCardId) {
        this.ownedCardId = ownedCardId;
        return this;
    }

    public OwnedCardValueBuilder setGrade(Double grade) {
        this.grade = grade;
        return this;
    }

    public OwnedCardValueBuilder setValueInCents(long valueInCents) {
        this.valueInCents = valueInCents;
        return this;
    }

    public OwnedCardValueBuilder setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public OwnedCardValue build() {
        return new OwnedCardValue(ownedCardId, grade, valueInCents, timestamp);
    }
}

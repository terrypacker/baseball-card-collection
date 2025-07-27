package com.terrypacker.cardcollection.entity.cardvalue;

import java.time.ZonedDateTime;

public class OwnedCardValue {

    private Integer ownedCardId;
    private Double grade;
    private long valueInCents;
    private ZonedDateTime timestamp;

    public OwnedCardValue(Integer ownedCardId, Double grade, long valueInCents,
        ZonedDateTime timestamp) {
        this.ownedCardId = ownedCardId;
        this.grade = grade;
        this.valueInCents = valueInCents;
        this.timestamp = timestamp;
    }

    public Integer getOwnedCardId() {
        return ownedCardId;
    }

    public void setOwnedCardId(Integer ownedCardId) {
        this.ownedCardId = ownedCardId;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public long getValueInCents() {
        return valueInCents;
    }

    public void setValueInCents(long valueInCents) {
        this.valueInCents = valueInCents;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

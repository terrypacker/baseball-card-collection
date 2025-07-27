package com.terrypacker.cardcollection.entity.ownedcard;

public class OwnedCardBuilder {

    private Integer id;
    private int baseballCardId;
    private String cardIdentifier;
    private String lot;
    private String notes;

    public static OwnedCardBuilder get() {return new OwnedCardBuilder();}

    public OwnedCardBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public OwnedCardBuilder setBaseballCardId(int baseballCardId) {
        this.baseballCardId = baseballCardId;
        return this;
    }

    public OwnedCardBuilder setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier = cardIdentifier;
        return this;
    }

    public OwnedCardBuilder setLot(String lot) {
        this.lot = lot;
        return this;
    }

    public OwnedCardBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public OwnedCard build() {
        return new OwnedCard(id, baseballCardId, cardIdentifier, lot, notes);
    }
}

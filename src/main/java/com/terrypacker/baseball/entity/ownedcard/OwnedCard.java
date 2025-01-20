package com.terrypacker.baseball.entity.ownedcard;

import com.terrypacker.baseball.entity.IdEntity;

/**
 * @author Terry Packer
 */
public class OwnedCard extends IdEntity {
    private Integer id;
    private Integer baseballCardId;
    private String cardIdentifier;
    private String notes;

    public OwnedCard(Integer id, Integer baseballCardId, String cardIdentifier, String notes) {
        this.id = id;
        this.baseballCardId = baseballCardId;
        this.cardIdentifier = cardIdentifier;
        this.notes = notes;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBaseballCardId() {
        return baseballCardId;
    }

    public void setBaseballCardId(Integer baseballCardId) {
        this.baseballCardId = baseballCardId;
    }

    public String getCardIdentifier() {
        return cardIdentifier;
    }

    public void setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier = cardIdentifier;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

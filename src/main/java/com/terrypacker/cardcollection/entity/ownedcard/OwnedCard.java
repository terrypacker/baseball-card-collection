package com.terrypacker.cardcollection.entity.ownedcard;

import com.terrypacker.cardcollection.entity.IdEntity;

/**
 * @author Terry Packer
 */
public class OwnedCard extends IdEntity {
    private Integer collectorCardId;
    private String cardIdentifier;
    private String lot;
    private String notes;

    public OwnedCard(Integer id, Integer collectorCardId, String cardIdentifier, String lot, String notes) {
        this.id = id;
        this.collectorCardId = collectorCardId;
        this.cardIdentifier = cardIdentifier;
        this.lot = lot;
        this.notes = notes;
    }

    public Integer getCollectorCardId() {
        return collectorCardId;
    }

    public void setCollectorCardId(Integer collectorCardId) {
        this.collectorCardId = collectorCardId;
    }

    public String getCardIdentifier() {
        return cardIdentifier;
    }

    public void setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier = cardIdentifier;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

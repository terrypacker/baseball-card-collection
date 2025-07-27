package com.terrypacker.cardcollection.entity.card;

import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import java.util.List;

public class CardBuilder {

    private Integer id;
    private Sport sport;
    private String playerName;
    private String teamName;
    private String brand;
    private Integer cardNumber;
    private Integer year;
    private String notes;
    private List<OwnedCard> ownedCards;

    public static CardBuilder get() {
        return new CardBuilder();
    }

    public CardBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public CardBuilder setSport(Sport sport) {
        this.sport = sport;
        return this;
    }

    public CardBuilder setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public CardBuilder setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public CardBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CardBuilder setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public CardBuilder setYear(Integer year) {
        this.year = year;
        return this;
    }

    public CardBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public CardBuilder setOwnedCards(List<OwnedCard> ownedCards) {
        this.ownedCards = ownedCards;
        return this;
    }

    public Card build() {
        return new Card(id, sport, playerName, teamName, brand, cardNumber, year, notes, ownedCards);
    }
}

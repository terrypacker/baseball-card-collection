package com.terrypacker.cardcollection.entity.card;

import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import java.util.List;

public class CollectorCardBuilder {

    private Integer id;
    private Sport sport;
    private String playerName;
    private String teamName;
    private String brand;
    private Integer cardNumber;
    private Integer year;
    private String notes;
    private List<OwnedCard> ownedCards;

    public static CollectorCardBuilder get() {
        return new CollectorCardBuilder();
    }

    public CollectorCardBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public CollectorCardBuilder setSport(Sport sport) {
        this.sport = sport;
        return this;
    }

    public CollectorCardBuilder setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public CollectorCardBuilder setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public CollectorCardBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CollectorCardBuilder setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public CollectorCardBuilder setYear(Integer year) {
        this.year = year;
        return this;
    }

    public CollectorCardBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public CollectorCardBuilder setOwnedCards(List<OwnedCard> ownedCards) {
        this.ownedCards = ownedCards;
        return this;
    }

    public CollectorCard build() {
        return new CollectorCard(id, sport, playerName, teamName, brand, cardNumber, year, notes, ownedCards);
    }
}

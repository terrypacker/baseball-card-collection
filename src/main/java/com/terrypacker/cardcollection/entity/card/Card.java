package com.terrypacker.cardcollection.entity.card;


import com.terrypacker.cardcollection.entity.IdEntity;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import java.util.List;

public class Card extends IdEntity {

    private Sport sport;
    private String playerName;
    private String teamName;
    private String brand;
    private Integer cardNumber;
    private Integer year;
    private String notes;
    private List<OwnedCard> ownedCards;

    public Card(Integer id, Sport sport, String playerName, String teamName, String brand,
        Integer cardNumber,
        Integer year, String notes, List<OwnedCard> ownedCards) {
        this.id = id;
        this.sport = sport;
        this.playerName = playerName;
        this.teamName = teamName;
        this.brand = brand;
        this.cardNumber = cardNumber;
        this.year = year;
        this.notes = notes;
        this.ownedCards = ownedCards;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<OwnedCard> getOwnedCards() {
        return ownedCards;
    }

    public void setOwnedCards(List<OwnedCard> ownedCards) {
        this.ownedCards = ownedCards;
    }

    @Override
    public String toString() {
        return "BaseballCard{" +
            "id=" + id +
            ", playerName='" + playerName + '\'' +
            ", teamName='" + teamName + '\'' +
            ", brand='" + brand + '\'' +
            ", cardNumber=" + cardNumber +
            ", year=" + year +
            ", notes='" + notes + '\'' +
            '}';
    }
}

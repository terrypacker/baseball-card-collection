package com.terrypacker.baseball.entity.baseballcard;

public class BaseballCardBuilder {

    private Integer id;
    private String playerName;
    private String teamName;
    private String brand;
    private int cardNumber;
    private int year;
    private String notes;

    public static BaseballCardBuilder get() {
        return new BaseballCardBuilder();
    }

    public BaseballCardBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public BaseballCardBuilder setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public BaseballCardBuilder setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public BaseballCardBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public BaseballCardBuilder setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public BaseballCardBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    public BaseballCardBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public BaseballCard build() {
        return new BaseballCard(id, playerName, teamName, brand, cardNumber, year, notes);
    }
}

package com.terrypacker.baseball.entity;


public class BaseballCard {
    private Integer id;
    private String playerName;
    private String teamName;
    private String brand;
    private int cardNumber;
    private int year;
    private String notes;

    public BaseballCard(Integer id, String playerName, String teamName, String brand, int cardNumber, int year, String notes) {
        this.id = id;
        this.playerName = playerName;
        this.teamName = teamName;
        this.brand = brand;
        this.cardNumber = cardNumber;
        this.year = year;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

package com.terrypacker.cardcollection.ui.view.collection;

import com.terrypacker.cardcollection.repository.AbstractPropertyFilter;
import com.terrypacker.cardcollection.repository.collection.CardInCollectionRecordFilter;

public class CardInCollectionFilter extends CardInCollectionRecordFilter {
    private final CardInCollectionDataProvider dataProvider;

    public CardInCollectionFilter(CardInCollectionDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public void setId(Integer id) {
        this.id.setFilterValue(id);
        filterSet(this.id);
    }

    public void setSport(String sport) {
        this.sport.setFilterValue(sport);
        filterSet(this.sport);
    }

    public void setPlayerName(String playerName) {
        this.playerName.setFilterValue(playerName);
        filterSet(this.playerName);
    }

    public void setTeamName(String teamName) {
        this.teamName.setFilterValue(teamName);
        filterSet(this.teamName);
    }

    public void setBrand(String brand) {
        this.brand.setFilterValue(brand);
        filterSet(this.brand);
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber.setFilterValue(cardNumber);
        filterSet(this.cardNumber);
    }

    public void setYear(Integer year) {
        this.year.setFilterValue(year);
        filterSet(this.year);
    }

    public void setNotes(String notes) {
        this.notes.setFilterValue(notes);
        filterSet(this.notes);
    }

    @Override
    public void filterSet(AbstractPropertyFilter filter) {
        this.dataProvider.setFilter(this);
    }
}

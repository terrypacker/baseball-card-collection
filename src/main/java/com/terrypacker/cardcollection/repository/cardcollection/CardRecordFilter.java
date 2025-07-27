package com.terrypacker.cardcollection.repository.cardcollection;

import com.terrypacker.cardcollection.db.tables.Baseballcard;
import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.repository.EntityFilter;
import com.terrypacker.cardcollection.repository.Filter.Matcher;
import com.terrypacker.cardcollection.repository.PropertyIntegerFilter;
import com.terrypacker.cardcollection.repository.PropertyStringFilter;

public class CardRecordFilter extends EntityFilter {

    protected final PropertyIntegerFilter id;
    protected final PropertyStringFilter sport;
    protected final PropertyStringFilter playerName;
    protected final PropertyStringFilter teamName;
    protected final PropertyStringFilter brand;
    protected final PropertyIntegerFilter cardNumber;
    protected final PropertyIntegerFilter year;
    protected final PropertyStringFilter notes;

    public CardRecordFilter() {

        this.id = new PropertyIntegerFilter();
        this.id.setField(Baseballcard.BASEBALLCARD.ID);
        this.id.setMatcher(Matcher.EQUALS);

        this.sport = new PropertyStringFilter();
        this.sport.setField(Baseballcard.BASEBALLCARD.SPORT);
        this.sport.setMatcher(Matcher.CONTAINS);

        this.playerName = new PropertyStringFilter();
        this.playerName.setField(Baseballcard.BASEBALLCARD.PLAYER);
        this.playerName.setMatcher(Matcher.CONTAINS);

        this.teamName = new PropertyStringFilter();
        this.teamName.setField(Baseballcard.BASEBALLCARD.TEAM);
        this.teamName.setMatcher(Matcher.CONTAINS);

        this.brand = new PropertyStringFilter();
        this.brand.setField(Baseballcard.BASEBALLCARD.BRAND);
        this.brand.setMatcher(Matcher.CONTAINS);

        this.cardNumber = new PropertyIntegerFilter();
        this.cardNumber.setField(Baseballcard.BASEBALLCARD.CARDNUMBER);
        this.cardNumber.setMatcher(Matcher.EQUALS);

        this.year = new PropertyIntegerFilter();
        this.year.setField(Baseballcard.BASEBALLCARD.YEAR);
        this.year.setMatcher(Matcher.EQUALS);

        this.notes = new PropertyStringFilter();
        this.notes.setField(Baseballcard.BASEBALLCARD.NOTES);
        this.notes.setMatcher(Matcher.CONTAINS);

        this.filters.add(id);
        filters.add(playerName);
        filters.add(teamName);
        filters.add(brand);
        filters.add(cardNumber);
        filters.add(year);
        filters.add(notes);
    }

    public static CardRecordFilter fromExample(Card card) {
        CardRecordFilter filter = new CardRecordFilter();
        filter.setMatcher(Matcher.EQUALS);
        if(card.getId() != null) {
            filter.setId(card.getId());
        }
        if(card.getSport() != null) {
            filter.setSport(card.getSport().name());
        }
        if(card.getPlayerName() != null) {
            filter.setPlayerName(card.getPlayerName());
        }
        if(card.getTeamName() != null) {
            filter.setTeamName(card.getTeamName());
        }
        if(card.getBrand() != null) {
            filter.setBrand(card.getBrand());
        }
        if(card.getCardNumber() != null) {
            filter.setCardNumber(card.getCardNumber());
        }
        if(card.getYear() != null) {
            filter.setYear(card.getYear());
        }
        if(card.getNotes() != null) {
            filter.setNotes(card.getNotes());
        }

        return filter;
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
}

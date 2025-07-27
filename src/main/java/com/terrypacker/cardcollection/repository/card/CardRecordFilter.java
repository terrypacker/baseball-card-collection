package com.terrypacker.cardcollection.repository.card;

import com.terrypacker.cardcollection.db.tables.Collectorcard;
import com.terrypacker.cardcollection.entity.card.CollectorCard;
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
        this.id.setField(Collectorcard.COLLECTORCARD.ID);
        this.id.setMatcher(Matcher.EQUALS);

        this.sport = new PropertyStringFilter();
        this.sport.setField(Collectorcard.COLLECTORCARD.SPORT);
        this.sport.setMatcher(Matcher.CONTAINS);

        this.playerName = new PropertyStringFilter();
        this.playerName.setField(Collectorcard.COLLECTORCARD.PLAYER);
        this.playerName.setMatcher(Matcher.CONTAINS);

        this.teamName = new PropertyStringFilter();
        this.teamName.setField(Collectorcard.COLLECTORCARD.TEAM);
        this.teamName.setMatcher(Matcher.CONTAINS);

        this.brand = new PropertyStringFilter();
        this.brand.setField(Collectorcard.COLLECTORCARD.BRAND);
        this.brand.setMatcher(Matcher.CONTAINS);

        this.cardNumber = new PropertyIntegerFilter();
        this.cardNumber.setField(Collectorcard.COLLECTORCARD.CARDNUMBER);
        this.cardNumber.setMatcher(Matcher.EQUALS);

        this.year = new PropertyIntegerFilter();
        this.year.setField(Collectorcard.COLLECTORCARD.YEAR);
        this.year.setMatcher(Matcher.EQUALS);

        this.notes = new PropertyStringFilter();
        this.notes.setField(Collectorcard.COLLECTORCARD.NOTES);
        this.notes.setMatcher(Matcher.CONTAINS);

        this.filters.add(id);
        filters.add(playerName);
        filters.add(teamName);
        filters.add(brand);
        filters.add(cardNumber);
        filters.add(year);
        filters.add(notes);
    }

    public static CardRecordFilter fromExample(CollectorCard collectorCard) {
        CardRecordFilter filter = new CardRecordFilter();
        filter.setMatcher(Matcher.EQUALS);
        if(collectorCard.getId() != null) {
            filter.setId(collectorCard.getId());
        }
        if(collectorCard.getSport() != null) {
            filter.setSport(collectorCard.getSport().name());
        }
        if(collectorCard.getPlayerName() != null) {
            filter.setPlayerName(collectorCard.getPlayerName());
        }
        if(collectorCard.getTeamName() != null) {
            filter.setTeamName(collectorCard.getTeamName());
        }
        if(collectorCard.getBrand() != null) {
            filter.setBrand(collectorCard.getBrand());
        }
        if(collectorCard.getCardNumber() != null) {
            filter.setCardNumber(collectorCard.getCardNumber());
        }
        if(collectorCard.getYear() != null) {
            filter.setYear(collectorCard.getYear());
        }
        if(collectorCard.getNotes() != null) {
            filter.setNotes(collectorCard.getNotes());
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

package com.terrypacker.baseball.ui.collection;

import com.terrypacker.baseball.db.tables.Baseballcard;
import com.terrypacker.baseball.repository.Filter;
import com.terrypacker.baseball.repository.Filter.Matcher;
import com.terrypacker.baseball.repository.PropertyIntegerFilter;
import com.terrypacker.baseball.repository.PropertyStringFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Terry Packer
 */
public class BaseballCardFilter {

    private final BaseballCardDataProvider dataProvider;
    private final List<Filter> filters = new ArrayList<>();

    private final PropertyIntegerFilter id;
    private final PropertyStringFilter playerName;
    private final PropertyStringFilter teamName;
    private final PropertyStringFilter brand;
    private final PropertyIntegerFilter cardNumber;
    private final PropertyIntegerFilter year;
    private final PropertyStringFilter notes;

    public BaseballCardFilter(
        BaseballCardDataProvider dataProvider) {
        this.dataProvider = dataProvider;

        this.id = new PropertyIntegerFilter();
        this.id.setField(Baseballcard.BASEBALLCARD.ID);
        this.id.setMatcher(Matcher.EQUALS);

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

    public void setId(Integer id) {
        this.id.setFilterValue(id);
        this.dataProvider.setFilter(this);
    }

    public void setPlayerName(String playerName) {
        this.playerName.setFilterValue(playerName);
        this.dataProvider.setFilter(this);
    }

    public void setTeamName(String teamName) {
        this.teamName.setFilterValue(teamName);
        this.dataProvider.setFilter(this);
    }

    public void setBrand(String brand) {
        this.brand.setFilterValue(brand);
        this.dataProvider.setFilter(this);
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber.setFilterValue(cardNumber);
        this.dataProvider.setFilter(this);
    }

    public void setYear(int year) {
        this.year.setFilterValue(year);
        this.dataProvider.setFilter(this);
    }

    public void setNotes(String notes) {
        this.notes.setFilterValue(notes);
        this.dataProvider.setFilter(this);
    }

    public List<Filter> getFilters() {
        return filters;
    }
}

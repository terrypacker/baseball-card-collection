package com.terrypacker.baseball.ui.view.ownedcard;

import com.terrypacker.baseball.db.tables.Baseballcard;
import com.terrypacker.baseball.db.tables.Ownedcard;
import com.terrypacker.baseball.repository.EntityFilter;
import com.terrypacker.baseball.repository.Filter;
import com.terrypacker.baseball.repository.Filter.Matcher;
import com.terrypacker.baseball.repository.PropertyIntegerFilter;
import com.terrypacker.baseball.repository.PropertyStringFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Terry Packer
 */
public class OwnedCardFilter implements EntityFilter {

    private final OwnedCardDataProvider dataProvider;
    private final List<Filter> filters = new ArrayList<>();

    private final PropertyIntegerFilter id;
    private final PropertyIntegerFilter baseballCardId;
    private final PropertyStringFilter cardIdentifier;
    private final PropertyStringFilter notes;

    public OwnedCardFilter(OwnedCardDataProvider dataProvider) {
        this.dataProvider = dataProvider;

        this.id = new PropertyIntegerFilter();
        this.id.setField(Ownedcard.OWNEDCARD.ID);
        this.id.setMatcher(Matcher.EQUALS);

        this.baseballCardId = new PropertyIntegerFilter();
        this.baseballCardId.setField(Ownedcard.OWNEDCARD.BASEBALLCARDID);
        this.baseballCardId.setMatcher(Matcher.EQUALS);

        this.cardIdentifier = new PropertyStringFilter();
        this.cardIdentifier.setField(Ownedcard.OWNEDCARD.CARDIDENTIFIER);
        this.cardIdentifier.setMatcher(Matcher.CONTAINS);

        this.notes = new PropertyStringFilter();
        this.notes.setField(Baseballcard.BASEBALLCARD.NOTES);
        this.notes.setMatcher(Matcher.CONTAINS);

        this.filters.add(id);
        this.filters.add(baseballCardId);
        this.filters.add(cardIdentifier);
        this.filters.add(notes);
    }

    public void setId(Integer id) {
        this.id.setFilterValue(id);
        this.dataProvider.setFilter(this);
    }

    public void setBaseballCardId(Integer id) {
        this.baseballCardId.setFilterValue(id);
        this.dataProvider.setFilter(this);
    }

    public void setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier.setFilterValue(cardIdentifier);
        this.dataProvider.setFilter(this);
    }

    public void setNotes(String notes) {
        this.notes.setFilterValue(notes);
        this.dataProvider.setFilter(this);
    }

    @Override
    public List<Filter> getFilters() {
        return filters;
    }


}

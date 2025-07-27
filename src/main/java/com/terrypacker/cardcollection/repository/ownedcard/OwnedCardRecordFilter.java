package com.terrypacker.cardcollection.repository.ownedcard;

import com.terrypacker.cardcollection.db.tables.Collectorcard;
import com.terrypacker.cardcollection.db.tables.Ownedcard;
import com.terrypacker.cardcollection.repository.EntityFilter;
import com.terrypacker.cardcollection.repository.Filter.Matcher;
import com.terrypacker.cardcollection.repository.PropertyIntegerFilter;
import com.terrypacker.cardcollection.repository.PropertyStringFilter;

public class OwnedCardRecordFilter extends EntityFilter {

    protected final PropertyIntegerFilter id;
    protected final PropertyIntegerFilter collectorCardId;
    protected final PropertyStringFilter cardIdentifier;
    protected final PropertyStringFilter lot;
    protected final PropertyStringFilter notes;

    public OwnedCardRecordFilter() {

        this.id = new PropertyIntegerFilter();
        this.id.setField(Ownedcard.OWNEDCARD.ID);
        this.id.setMatcher(Matcher.EQUALS);

        this.collectorCardId = new PropertyIntegerFilter();
        this.collectorCardId.setField(Ownedcard.OWNEDCARD.COLLECTORCARDID);
        this.collectorCardId.setMatcher(Matcher.EQUALS);

        this.cardIdentifier = new PropertyStringFilter();
        this.cardIdentifier.setField(Ownedcard.OWNEDCARD.CARDIDENTIFIER);
        this.cardIdentifier.setMatcher(Matcher.CONTAINS);

        this.lot = new PropertyStringFilter();
        this.lot.setField(Ownedcard.OWNEDCARD.LOT);
        this.lot.setMatcher(Matcher.CONTAINS);

        this.notes = new PropertyStringFilter();
        this.notes.setField(Collectorcard.COLLECTORCARD.NOTES);
        this.notes.setMatcher(Matcher.CONTAINS);

        this.filters.add(id);
        this.filters.add(collectorCardId);
        this.filters.add(cardIdentifier);
        this.filters.add(lot);
        this.filters.add(notes);
    }

    public void setId(Integer id) {
        this.id.setFilterValue(id);
        filterSet(this.id);
    }

    public void setCollectorCardId(Integer id) {
        this.collectorCardId.setFilterValue(id);
        filterSet(this.collectorCardId);
    }

    public void setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier.setFilterValue(cardIdentifier);
        filterSet(this.cardIdentifier);
    }

    public void setLot(String lot) {
        this.lot.setFilterValue(lot);
        filterSet(this.lot);
    }

    public void setNotes(String notes) {
        this.notes.setFilterValue(notes);
        filterSet(this.notes);
    }
}

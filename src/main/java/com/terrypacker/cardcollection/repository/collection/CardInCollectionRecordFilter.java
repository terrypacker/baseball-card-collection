package com.terrypacker.cardcollection.repository.collection;

import com.terrypacker.cardcollection.db.tables.Collectorcard;
import com.terrypacker.cardcollection.db.tables.Ownedcard;
import com.terrypacker.cardcollection.repository.EntityFilter;
import com.terrypacker.cardcollection.repository.Filter.Matcher;
import com.terrypacker.cardcollection.repository.PropertyIntegerFilter;
import com.terrypacker.cardcollection.repository.PropertyStringFilter;

public class CardInCollectionRecordFilter extends EntityFilter {

    protected final PropertyIntegerFilter id;
    protected final PropertyStringFilter sport;
    protected final PropertyStringFilter playerName;
    protected final PropertyStringFilter teamName;
    protected final PropertyStringFilter brand;
    protected final PropertyIntegerFilter cardNumber;
    protected final PropertyIntegerFilter year;
    protected final PropertyStringFilter cardIdentifier;
    protected final PropertyStringFilter lot;
    protected final PropertyStringFilter notes;


    public CardInCollectionRecordFilter() {

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

        this.cardIdentifier = new PropertyStringFilter();
        this.cardIdentifier.setField(Ownedcard.OWNEDCARD.CARDIDENTIFIER);
        this.cardIdentifier.setMatcher(Matcher.CONTAINS);

        this.lot = new PropertyStringFilter();
        this.lot.setField(Ownedcard.OWNEDCARD.LOT);
        this.lot.setMatcher(Matcher.CONTAINS);

        this.filters.add(id);
        this.filters.add(playerName);
        this.filters.add(teamName);
        this.filters.add(brand);
        this.filters.add(cardNumber);
        this.filters.add(year);
        this.filters.add(notes);
        this.filters.add(cardIdentifier);
        this.filters.add(lot);
    }


}

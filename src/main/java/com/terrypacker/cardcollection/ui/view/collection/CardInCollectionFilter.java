package com.terrypacker.cardcollection.ui.view.collection;

import com.terrypacker.cardcollection.repository.AbstractPropertyFilter;
import com.terrypacker.cardcollection.repository.EntityFilter;
import com.terrypacker.cardcollection.repository.collection.CardInCollectionRecordFilter;

public class CardInCollectionFilter extends CardInCollectionRecordFilter {
    private final CardInCollectionDataProvider dataProvider;

    public CardInCollectionFilter(CardInCollectionDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void filterSet(AbstractPropertyFilter filter) {
        this.dataProvider.setFilter(this);
    }
}

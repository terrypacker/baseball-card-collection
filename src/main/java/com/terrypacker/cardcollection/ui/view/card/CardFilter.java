package com.terrypacker.cardcollection.ui.view.card;

import com.terrypacker.cardcollection.repository.AbstractPropertyFilter;
import com.terrypacker.cardcollection.repository.card.CardRecordFilter;

/**
 * @author Terry Packer
 */
public class CardFilter extends CardRecordFilter {

    private final CardDataProvider dataProvider;

    public CardFilter(
        CardDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void filterSet(AbstractPropertyFilter filter) {
        this.dataProvider.setFilter(this);
    }
}

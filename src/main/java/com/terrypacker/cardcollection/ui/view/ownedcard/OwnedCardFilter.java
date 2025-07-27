package com.terrypacker.cardcollection.ui.view.ownedcard;

import com.terrypacker.cardcollection.repository.AbstractPropertyFilter;
import com.terrypacker.cardcollection.repository.ownedcard.OwnedCardRecordFilter;

/**
 * @author Terry Packer
 */
public class OwnedCardFilter extends OwnedCardRecordFilter {

    private final OwnedCardDataProvider dataProvider;

    public OwnedCardFilter(OwnedCardDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void filterSet(AbstractPropertyFilter filter) {
        this.dataProvider.setFilter(this);
    }
}

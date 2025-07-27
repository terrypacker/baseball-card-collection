package com.terrypacker.cardcollection.ui.view.collection;

import com.terrypacker.cardcollection.entity.collection.CardInCollection;
import com.terrypacker.cardcollection.service.CardInCollectionService;
import com.terrypacker.cardcollection.ui.view.AbstractDataProvider;
import org.springframework.stereotype.Component;

@Component
public class CardInCollectionDataProvider extends
    AbstractDataProvider<CardInCollection, CardInCollectionFilter, CardInCollectionService> {

    public CardInCollectionDataProvider(CardInCollectionService service) {
        super(service);
    }

}
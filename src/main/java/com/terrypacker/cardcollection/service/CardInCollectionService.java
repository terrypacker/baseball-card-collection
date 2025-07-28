package com.terrypacker.cardcollection.service;

import com.terrypacker.cardcollection.entity.collection.CardInCollection;
import com.terrypacker.cardcollection.repository.collection.CardInCollectionJooqRepository;
import com.terrypacker.cardcollection.repository.collection.CardInCollectionRecordFilter;
import com.terrypacker.cardcollection.repository.collection.CardInCollectionRepository;
import org.springframework.stereotype.Service;

@Service
public class CardInCollectionService extends AbstractService<CardInCollection,
    CardInCollectionRepository, CardInCollectionRecordFilter> {

    public CardInCollectionService(CardInCollectionJooqRepository repository) {
        super(repository);
    }
}

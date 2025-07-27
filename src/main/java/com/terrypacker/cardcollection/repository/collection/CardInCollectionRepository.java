package com.terrypacker.cardcollection.repository.collection;

import com.terrypacker.cardcollection.entity.collection.CardInCollection;
import com.terrypacker.cardcollection.repository.ReactiveRepository;

public interface CardInCollectionRepository extends ReactiveRepository<CardInCollection, CardInCollectionRecordFilter> {

}

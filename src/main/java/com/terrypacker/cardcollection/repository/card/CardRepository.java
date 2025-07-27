package com.terrypacker.cardcollection.repository.card;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.repository.ReactiveRepository;

public interface CardRepository extends ReactiveRepository<CollectorCard, CardRecordFilter> {
}

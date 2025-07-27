package com.terrypacker.cardcollection.service;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.repository.cardcollection.CardJooqRepository;
import com.terrypacker.cardcollection.repository.cardcollection.CardRecordFilter;
import org.springframework.stereotype.Service;

@Service
public class CardService extends AbstractService<CollectorCard, CardJooqRepository, CardRecordFilter> {
    public CardService(CardJooqRepository baseballCardRepository) {
        super(baseballCardRepository);
    }
}

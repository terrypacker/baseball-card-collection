package com.terrypacker.cardcollection.service;

import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.repository.cardcollection.CardJooqRepository;
import com.terrypacker.cardcollection.repository.cardcollection.CardRecordFilter;
import org.springframework.stereotype.Service;

@Service
public class CardService extends AbstractService<Card, CardJooqRepository, CardRecordFilter> {
    public CardService(CardJooqRepository baseballCardRepository) {
        super(baseballCardRepository);
    }
}

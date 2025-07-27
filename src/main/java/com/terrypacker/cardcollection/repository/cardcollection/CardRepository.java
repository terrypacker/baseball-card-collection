package com.terrypacker.cardcollection.repository.cardcollection;

import com.terrypacker.cardcollection.entity.card.Card;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface CardRepository extends ReactiveSortingRepository<Card, Integer>,
    ReactiveCrudRepository<Card, Integer> {
}

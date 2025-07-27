package com.terrypacker.cardcollection.repository.cardcollection;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface CardRepository extends ReactiveSortingRepository<CollectorCard, Integer>,
    ReactiveCrudRepository<CollectorCard, Integer> {
}

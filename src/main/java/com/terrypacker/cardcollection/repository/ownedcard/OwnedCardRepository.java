package com.terrypacker.cardcollection.repository.ownedcard;

import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

/**
 * @author Terry Packer
 */
public interface OwnedCardRepository extends ReactiveSortingRepository<OwnedCard, Integer>,
    ReactiveCrudRepository<OwnedCard, Integer> {

    Flux<OwnedCard> getOwnedCardsByCollectorCardId(int collectorCardId);
}

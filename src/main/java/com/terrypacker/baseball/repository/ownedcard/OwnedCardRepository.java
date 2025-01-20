package com.terrypacker.baseball.repository.ownedcard;

import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

/**
 * @author Terry Packer
 */
public interface OwnedCardRepository extends ReactiveSortingRepository<OwnedCard, Integer>,
    ReactiveCrudRepository<OwnedCard, Integer> {
}

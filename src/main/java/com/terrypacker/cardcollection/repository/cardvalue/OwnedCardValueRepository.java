package com.terrypacker.cardcollection.repository.cardvalue;

import com.terrypacker.cardcollection.entity.cardvalue.OwnedCardValue;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Sort.Direction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
public interface OwnedCardValueRepository {

    Mono<OwnedCardValue> insert(OwnedCardValue ownedCardValue);

    /**
     * Get the most recent value for an owned card.
     * @param ownedCard
     * @return
     * @throws NoSuchElementException
     */
    Mono<OwnedCardValue> getLatestValue(OwnedCard ownedCard) throws NoSuchElementException;

    Flux<OwnedCardValue> getLatestValues(OwnedCard ownedCard, int limit, Direction direction);
}

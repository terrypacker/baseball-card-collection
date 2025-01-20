package com.terrypacker.baseball.repository.cardvalue;

import com.terrypacker.baseball.entity.cardvalue.OwnedCardValue;
import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import java.util.NoSuchElementException;
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

}

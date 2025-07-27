package com.terrypacker.cardcollection.service;

import com.terrypacker.cardcollection.entity.cardvalue.OwnedCardValue;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.repository.cardvalue.OwnedCardValueRepository;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
@Service
public class OwnedCardValueService {

    private final OwnedCardValueRepository ownedCardValueRepository;

    public OwnedCardValueService(OwnedCardValueRepository cardValueRepository) {
        this.ownedCardValueRepository = cardValueRepository;
    }

    public Mono<OwnedCardValue> getLatestOwnedCardValue(OwnedCard ownedCard) {
        return this.ownedCardValueRepository.getLatestValue(ownedCard);
    }

    public Flux<OwnedCardValue> getLatestValues(OwnedCard ownedCard, int limit, Direction direction) {
        return this.ownedCardValueRepository.getLatestValues(ownedCard, limit, direction);
    }
}

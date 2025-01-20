package com.terrypacker.baseball.service;

import com.terrypacker.baseball.entity.cardvalue.OwnedCardValue;
import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.repository.cardvalue.OwnedCardValueRepository;
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

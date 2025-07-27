package com.terrypacker.cardcollection.service;

import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.repository.ownedcard.OwnedCardJooqRepository;
import com.terrypacker.cardcollection.repository.ownedcard.OwnedCardRecordFilter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author Terry Packer
 */
@Service
public class OwnedCardService extends AbstractService<OwnedCard, OwnedCardJooqRepository,
    OwnedCardRecordFilter> {
    public OwnedCardService(OwnedCardJooqRepository ownedCardJooqRepository) {
        super(ownedCardJooqRepository);
    }
    
    public Flux<OwnedCard> getOwnedCardsByCollectorCardId(int cardId) {
        return repository.getOwnedCardsByCollectorCardId(cardId);
    }
}

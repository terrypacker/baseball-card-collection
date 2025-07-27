package com.terrypacker.cardcollection.service;

import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.repository.ownedcard.OwnedCardJooqRepository;
import com.terrypacker.cardcollection.ui.view.ownedcard.OwnedCardFilter;
import org.springframework.stereotype.Service;

/**
 * @author Terry Packer
 */
@Service
public class OwnedCardService extends AbstractService<OwnedCard, OwnedCardJooqRepository, OwnedCardFilter> {
    public OwnedCardService(OwnedCardJooqRepository ownedCardJooqRepository) {
        super(ownedCardJooqRepository);
    }
}

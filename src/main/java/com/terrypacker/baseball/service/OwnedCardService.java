package com.terrypacker.baseball.service;

import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.repository.ownedcard.OwnedCardJooqRepository;
import com.terrypacker.baseball.ui.view.ownedcard.OwnedCardFilter;
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

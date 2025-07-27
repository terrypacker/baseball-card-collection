package com.terrypacker.cardcollection.ui.view.ownedcard;

import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.service.OwnedCardService;
import com.terrypacker.cardcollection.ui.view.AbstractDataProvider;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class OwnedCardDataProvider extends AbstractDataProvider<OwnedCard, OwnedCardFilter, OwnedCardService> {

    public OwnedCardDataProvider(OwnedCardService ownedCardService) {
        super(ownedCardService);
    }

}

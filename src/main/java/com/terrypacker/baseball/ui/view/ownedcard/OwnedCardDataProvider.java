package com.terrypacker.baseball.ui.view.ownedcard;

import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.service.OwnedCardService;
import com.terrypacker.baseball.ui.view.AbstractDataProvider;
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

package com.terrypacker.cardcollection.ui.view.cardcollection;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.service.CardService;
import com.terrypacker.cardcollection.ui.view.AbstractDataProvider;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class CardDataProvider extends AbstractDataProvider<CollectorCard, CardFilter, CardService> {
    public CardDataProvider(CardService cardService) {
        super(cardService);
    }
}

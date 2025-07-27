package com.terrypacker.cardcollection.ui.view.cardcollection;

import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.service.CardService;
import com.vaadin.flow.component.combobox.ComboBox;

/**
 * @author Terry Packer
 */
public class CardSelect extends ComboBox<Card> {

    private final CardDataProvider dataProvider;
    private final CardFilter filter;

    public CardSelect(CardService service) {
        this.dataProvider = new CardDataProvider(service);
        this.filter = new CardFilter(dataProvider);
        CardFilter filter = new CardFilter(dataProvider);
        setDataProvider(dataProvider, filterText -> {
            filter.setPlayerName(filterText);
            dataProvider.setFilter(filter);
            return null;
        });
        setItemLabelGenerator(card -> {
            return card.getPlayerName() + " - " + card.getBrand() + " - " + card.getCardNumber() + " - " + card.getYear();
        });
    }

}

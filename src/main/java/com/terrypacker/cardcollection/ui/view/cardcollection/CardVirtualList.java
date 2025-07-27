package com.terrypacker.cardcollection.ui.view.cardcollection;

import com.terrypacker.cardcollection.entity.card.Card;
import com.vaadin.flow.component.virtuallist.VirtualList;
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public class CardVirtualList extends VirtualList<Card> {

    public CardVirtualList(CardDataProvider dataProvider, Consumer<Card> selectListener) {
        this.setDataProvider(dataProvider);
        this.setRenderer(new CardRenderer(c -> new CardTile(c, selectListener, false)));
    }
}

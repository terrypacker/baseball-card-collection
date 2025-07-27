package com.terrypacker.cardcollection.ui.view.card;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.vaadin.flow.component.virtuallist.VirtualList;
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public class CardVirtualList extends VirtualList<CollectorCard> {

    public CardVirtualList(CardDataProvider dataProvider, Consumer<CollectorCard> selectListener) {
        this.setDataProvider(dataProvider);
        this.setRenderer(new CardRenderer(c -> new CardTile(c, selectListener, false)));
    }
}

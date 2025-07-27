package com.terrypacker.cardcollection.entity.collection;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import java.util.List;

public class CardInCollectionBuilder {

    private CollectorCard collectorCard;
    private List<OwnedCard> ownedCards;

    public static CardInCollectionBuilder get() {
        return new CardInCollectionBuilder();
    }

    public CardInCollectionBuilder setCard(CollectorCard collectorCard) {
        this.collectorCard = collectorCard;
        return this;
    }

    public CardInCollectionBuilder setOwnedCards(List<OwnedCard> ownedCards) {
        this.ownedCards = ownedCards;
        return this;
    }

    public CardInCollection build() {
        return new CardInCollection(collectorCard, ownedCards);
    }
}

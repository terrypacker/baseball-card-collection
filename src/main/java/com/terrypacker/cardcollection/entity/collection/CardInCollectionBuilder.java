package com.terrypacker.cardcollection.entity.collection;

import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import java.util.List;

public class CardInCollectionBuilder {

    private Card card;
    private List<OwnedCard> ownedCards;

    public static CardInCollectionBuilder get() {
        return new CardInCollectionBuilder();
    }

    public CardInCollectionBuilder setCard(Card card) {
        this.card = card;
        return this;
    }

    public CardInCollectionBuilder setOwnedCards(List<OwnedCard> ownedCards) {
        this.ownedCards = ownedCards;
        return this;
    }

    public CardInCollection build() {
        return new CardInCollection(card, ownedCards);
    }
}

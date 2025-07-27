package com.terrypacker.cardcollection.entity.collection;

import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import java.util.List;

/**
 * Container for a card and its referenced data
 */
public class CardInCollection {
    private final Card card;
    private final List<OwnedCard> ownedCards;

    public CardInCollection(Card card, List<OwnedCard> ownedCards) {
        this.card = card;
        this.ownedCards = ownedCards;
    }

    public Card getCard() {
        return card;
    }

    public List<OwnedCard> getOwnedCards() {
        return ownedCards;
    }
}

package com.terrypacker.cardcollection.entity.collection;

import com.terrypacker.cardcollection.entity.IdEntity;
import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import java.util.List;

/**
 * Container for a card and its referenced data
 */
public class CardInCollection extends IdEntity {
    private final CollectorCard collectorCard;
    private final List<OwnedCard> ownedCards;

    public CardInCollection(CollectorCard collectorCard, List<OwnedCard> ownedCards) {
        this.collectorCard = collectorCard;
        this.ownedCards = ownedCards;
    }

    public CollectorCard getCard() {
        return collectorCard;
    }

    public List<OwnedCard> getOwnedCards() {
        return ownedCards;
    }
}

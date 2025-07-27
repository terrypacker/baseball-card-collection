package com.terrypacker.cardcollection.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.entity.collection.CardInCollection;
import com.terrypacker.cardcollection.entity.collection.CardInCollectionBuilder;
import com.terrypacker.cardcollection.entity.collection.CardInCollectionCsvMappingStrategy;
import com.terrypacker.cardcollection.repository.cardcollection.CardRecordFilter;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Service to perform collection wide operations
 */
@Service
public class CollectionService {

    private final CardService cardService;
    private final OwnedCardService ownedCardService;
    private final OwnedCardValueService ownedCardValueService;

    public CollectionService(CardService cardService, OwnedCardService ownedCardService, OwnedCardValueService ownedCardValueService) {
        this.cardService = cardService;
        this.ownedCardService = ownedCardService;
        this.ownedCardValueService = ownedCardValueService;
    }

    /**
     * Import cards, the stream is expected to be closed after this call
     * @param reader
     * @param cardConsumer
     */
    public void importCards(BufferedReader reader, Consumer<CardInCollection> cardConsumer) {
        CsvToBean<CardInCollection> csvReader =
            new CsvToBeanBuilder(reader).withMappingStrategy(
                    new CardInCollectionCsvMappingStrategy())
                .withSeparator(',').withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();

        Iterator<CardInCollection> it = csvReader.iterator();
        while (it.hasNext()) {
            CardInCollection cardInCollection = it.next();
            //Do we have this card
            CardRecordFilter filter = CardRecordFilter.fromExample(cardInCollection.getCard());
            Optional<Card> card = cardService.findByExample(filter).blockOptional();
            Card savedCard = card.orElseGet(
                () -> this.cardService.save(cardInCollection.getCard()).block());
            //TODO Save Card and Owned Cards
            cardConsumer.accept(CardInCollectionBuilder.get().setCard(savedCard).setOwnedCards(cardInCollection.getOwnedCards()).build());
        }
    }

}

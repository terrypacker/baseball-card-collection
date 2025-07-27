package com.terrypacker.cardcollection.service;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.entity.collection.CardInCollection;
import com.terrypacker.cardcollection.entity.collection.CardInCollectionBuilder;
import com.terrypacker.cardcollection.entity.collection.CardInCollectionCsvMappingStrategy;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.repository.card.CardRecordFilter;
import com.terrypacker.cardcollection.repository.ownedcard.OwnedCardRecordFilter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
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
     * @param reader - reader of stream
     * @param cardConsumer - consume the results per card
     */
    public void importCollection(BufferedReader reader, Consumer<CardInCollection> cardConsumer) {
        CsvToBean<CardInCollection> csvReader =
            new CsvToBeanBuilder(reader).withMappingStrategy(
                    new CardInCollectionCsvMappingStrategy())
                .withSeparator(',').withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();

        for (CardInCollection cardInCollection : csvReader) {
            //Find or create this card
            CardRecordFilter filter = CardRecordFilter.fromExample(cardInCollection.getCard());
            Optional<CollectorCard> card = cardService.findByExample(filter).blockOptional();
            CollectorCard savedCollectorCard = card.orElseGet(
                () -> this.cardService.save(cardInCollection.getCard()).block());

            List<OwnedCard> savedOwnedCards = new ArrayList<>();
            if(!cardInCollection.getOwnedCards().isEmpty()) {
                //Only insert owned cards if there are none in the database
                OwnedCardRecordFilter ownedCardFilter = new OwnedCardRecordFilter();
                ownedCardFilter.setCollectorCardId(savedCollectorCard.getId());
                int count = ownedCardService.count(Optional.of(ownedCardFilter));
                if(count == 0) {
                    savedOwnedCards = cardInCollection.getOwnedCards().stream().map(ownedCard -> {
                        ownedCard.setCollectorCardId(savedCollectorCard.getId());
                        return ownedCardService.save(ownedCard).block();
                    }).toList();
                }
            }

            cardConsumer.accept(CardInCollectionBuilder.get().setCard(savedCollectorCard)
                .setOwnedCards(savedOwnedCards).build());
        }
    }

    /**
     * Export the entire collection as a CSV
     * @param writer - where to write
     * @throws CsvRequiredFieldEmptyException - if there is no available field mapping
     * @throws CsvDataTypeMismatchException - if the data mismatches what is expecte
     */
    public void exportCollection(BufferedWriter writer) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Stream<CollectorCard> cards = cardService.findAll().toStream();
        Stream<CardInCollection> collection = cards.map(card -> CardInCollectionBuilder.get()
                .setCard(card)
                .setOwnedCards(
                    ownedCardService.getOwnedCardsByCollectorCardId(card.getId()).collectList().block())
                .build());

        StatefulBeanToCsv<CardInCollection> sbc =
            new StatefulBeanToCsvBuilder<CardInCollection>(writer)
                .withQuotechar('\"')
                .withApplyQuotesToAll(false)
                .withMappingStrategy(new CardInCollectionCsvMappingStrategy())
                .withSeparator(ICSVWriter.DEFAULT_SEPARATOR).build();

        sbc.write(collection);
    }

}

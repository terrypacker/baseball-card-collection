package com.terrypacker.cardcollection.entity.collection;

import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.terrypacker.cardcollection.entity.AbstractCsvMappingStrategy;
import com.terrypacker.cardcollection.entity.card.CollectorCardBuilder;
import com.terrypacker.cardcollection.entity.card.Sport;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCardBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Terry Packer
 */
public class CardInCollectionCsvMappingStrategy extends
    AbstractCsvMappingStrategy<CardInCollection> {

    private static final String[] headers =
        new String[] {
            "sport",
            "playerName",
            "teamName",
            "brand",
            "cardNumber",
            "year",
            "count",
            "notes"};

    @Override
    public String[] getHeaderNames() {
        return headers;
    }


    @Override
    public CardInCollection populateNewBean(String[] line)
        throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException {
        CollectorCardBuilder collectorCardBuilder = CollectorCardBuilder.get()
            .setSport(Sport.valueOf(line[0]))
            .setPlayerName(line[1])
            .setTeamName(line[2])
            .setBrand(line[3])
            .setCardNumber(Integer.parseInt(line[4]))
            .setYear(Integer.parseInt(line[5]))
            .setNotes(line[7]);

        int count = Integer.parseInt(line[6]);
        List<OwnedCard> ownedCards = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            ownedCards.add(OwnedCardBuilder.get().build());
        }
        return CardInCollectionBuilder.get()
            .setCard(collectorCardBuilder.build())
            .setOwnedCards(ownedCards).build();
    }

    @Override
    public String[] transmuteBean(CardInCollection bean)
        throws CsvFieldAssignmentException, CsvChainedException {
        String[] row = new String[headers.length];
        row[0] = bean.getCard().getSport().name();
        row[1] = bean.getCard().getPlayerName();
        row[2] = bean.getCard().getTeamName();
        row[3] = bean.getCard().getBrand();
        row[4] = Integer.toString(bean.getCard().getCardNumber());
        row[5] = Integer.toString(bean.getCard().getYear());
        row[6] = Integer.toString(bean.getOwnedCards().size());
        row[7] = bean.getCard().getNotes();
        return row;
    }
}

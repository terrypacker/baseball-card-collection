package com.terrypacker.cardcollection.entity.card;

import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.terrypacker.cardcollection.entity.AbstractCsvMappingStrategy;

/**
 * @author Terry Packer
 */
public class CardCsvMappingStrategy extends
    AbstractCsvMappingStrategy<Card> {

    private static final String[] headers =
        new String[]{"id", "sport", "playerName", "teamName", "brand",
            "cardNumber", "year", "notes"};

    @Override
    public String[] getHeaderNames() {
        return headers;
    }


    @Override
    public Card populateNewBean(String[] line)
        throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException {
        return CardBuilder.get()
            .setId(Integer.parseInt(line[0]))
            .setSport(Sport.valueOf(line[1]))
            .setPlayerName(line[2])
            .setTeamName(line[3])
            .setBrand(line[4])
            .setCardNumber(Integer.parseInt(line[5]))
            .setYear(Integer.parseInt(line[6]))
            .setNotes(line[7]).build();
    }

    @Override
    public String[] transmuteBean(Card bean)
        throws CsvFieldAssignmentException, CsvChainedException {
        String[] row = new String[headers.length];
        row[0] = Integer.toString(bean.getId());
        row[1] = bean.getSport().name();
        row[2] = bean.getPlayerName();
        row[3] = bean.getTeamName();
        row[4] = bean.getBrand();
        row[5] = Integer.toString(bean.getCardNumber());
        row[6] = Integer.toString(bean.getYear());
        row[7] = bean.getNotes();
        return row;
    }
}

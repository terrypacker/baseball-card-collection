package com.terrypacker.baseball.entity.ownedcard;

import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.terrypacker.baseball.entity.AbstractCsvMappingStrategy;

/**
 * @author Terry Packer
 */
public class OwnedCardCsvMappingStrategy extends
    AbstractCsvMappingStrategy<OwnedCard> {

    private static final String[] headers =
        new String[]{"id", "ownedCardId", "cardIdentifier", "notes"};


    @Override
    public String[] getHeaderNames() {
        return headers;
    }

    @Override
    public OwnedCard populateNewBean(String[] line)
        throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException {
        return OwnedCardBuilder.get()
            .setId(Integer.parseInt(line[0]))
            .setBaseballCardId(Integer.parseInt(line[1]))
            .setCardIdentifier(line[2])
            .setNotes(line[3]).build();
    }

    @Override
    public String[] transmuteBean(OwnedCard bean)
        throws CsvFieldAssignmentException, CsvChainedException {
        String[] row = new String[headers.length];
        row[0] = Integer.toString(bean.getId());
        row[1] = Integer.toString(bean.getBaseballCardId());
        row[2] = bean.getCardIdentifier();
        row[3] = bean.getNotes();
        return row;
    }
}

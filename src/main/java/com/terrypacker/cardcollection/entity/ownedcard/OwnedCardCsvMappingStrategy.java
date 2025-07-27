package com.terrypacker.cardcollection.entity.ownedcard;

import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.terrypacker.cardcollection.entity.AbstractCsvMappingStrategy;

/**
 * @author Terry Packer
 */
public class OwnedCardCsvMappingStrategy extends
    AbstractCsvMappingStrategy<OwnedCard> {

    private static final String[] headers =
        new String[]{"id", "baseballCardId", "cardIdentifier", "lot", "notes"};


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
            .setLot(line[3])
            .setNotes(line[4]).build();
    }

    @Override
    public String[] transmuteBean(OwnedCard bean)
        throws CsvFieldAssignmentException, CsvChainedException {
        String[] row = new String[headers.length];
        row[0] = Integer.toString(bean.getId());
        row[1] = Integer.toString(bean.getCollectorCardId());
        row[2] = bean.getCardIdentifier();
        row[3] = bean.getLot();
        row[4] = bean.getNotes();
        return row;
    }
}

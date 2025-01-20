package com.terrypacker.baseball.entity.cardvalue;

import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.terrypacker.baseball.entity.AbstractCsvMappingStrategy;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Terry Packer
 */
public class OwnedCardValueCsvMappingStrategy extends
    AbstractCsvMappingStrategy<OwnedCardValue> {

    private static final String[] headers =
        new String[]{"ownedCardId", "grade", "value", "time"};

    @Override
    public String[] getHeaderNames() {
        return headers;
    }

    @Override
    public OwnedCardValue populateNewBean(String[] line)
        throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException {
        return OwnedCardValueBuilder.get()
            .setOwnedCardId(Integer.parseInt(line[0]))
            .setGrade(Double.parseDouble(line[1]))
            .setValueInCents(Long.parseLong(line[2]))
            .setTimestamp(ZonedDateTime.parse(line[3], DateTimeFormatter.ISO_ZONED_DATE_TIME))
            .build();
    }

    @Override
    public String[] transmuteBean(OwnedCardValue bean)
        throws CsvFieldAssignmentException, CsvChainedException {
        String[] row = new String[headers.length];
        row[0] = Integer.toString(bean.getOwnedCardId());
        row[1] = Double.toString(bean.getGrade());
        row[2] = Long.toString(bean.getValueInCents());
        row[3] = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(bean.getTimestamp());
        return row;
    }
}

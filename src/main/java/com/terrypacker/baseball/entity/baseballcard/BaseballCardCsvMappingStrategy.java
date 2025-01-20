package com.terrypacker.baseball.entity.baseballcard;

import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.terrypacker.baseball.entity.AbstractCsvMappingStrategy;

/**
 * @author Terry Packer
 */
public class BaseballCardCsvMappingStrategy extends
    AbstractCsvMappingStrategy<BaseballCard> {

    private static final String[] headers =
        new String[]{"id", "playerName", "teamName", "brand",
            "cardNumber", "year", "notes"};

    @Override
    public String[] getHeaderNames() {
        return headers;
    }


    @Override
    public BaseballCard populateNewBean(String[] line)
        throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException {
        return BaseballCardBuilder.get()
            .setId(Integer.parseInt(line[0]))
            .setPlayerName(line[1])
            .setTeamName(line[2])
            .setBrand(line[3])
            .setCardNumber(Integer.parseInt(line[4]))
            .setYear(Integer.parseInt(line[5]))
            .setNotes(line[6]).build();
    }

    @Override
    public String[] transmuteBean(BaseballCard bean)
        throws CsvFieldAssignmentException, CsvChainedException {
        String[] row = new String[headers.length];
        row[0] = Integer.toString(bean.getId());
        row[1] = bean.getPlayerName();
        row[2] = bean.getTeamName();
        row[3] = bean.getBrand();
        row[4] = Integer.toString(bean.getCardNumber());
        row[5] = Integer.toString(bean.getYear());
        row[6] = bean.getNotes();
        return row;
    }
}

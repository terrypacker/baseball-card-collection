package com.terrypacker.baseball.entity;

import com.opencsv.CSVReader;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;

/**
 * @author Terry Packer
 */
public abstract class AbstractCsvMappingStrategy<T> implements MappingStrategy<T> {

    public abstract String[] getHeaderNames();

    @Override
    public void captureHeader(CSVReader reader) throws IOException, CsvRequiredFieldEmptyException {
        String[] headerNames = getHeaderNames();
        String[] row = null;
        try {
            row = reader.readNext();
        } catch (CsvValidationException e) {
            throw new IOException(e);
        }
        if (row.length != headerNames.length) {
            throw new CsvRequiredFieldEmptyException("Incorrect number of headers");
        }
        for (int i = 0; i < row.length; i++) {
            if (!headerNames[i].equals(row[i])) {
                throw new CsvRequiredFieldEmptyException(headerNames[i]);
            }
        }
    }


    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        return getHeaderNames();
    }

    @Override
    public void setType(Class<? extends T> type) throws CsvBadConverterException {

    }

}

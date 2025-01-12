package com.terrypacker.baseball.repository;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.csv.CSVReader;
import com.terrypacker.baseball.entity.BaseballCard;
import com.terrypacker.baseball.entity.BaseballCardBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class DefaultCardLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final Resource cardDataFile;
    private final BaseballCardRepository cardRepository;

    public DefaultCardLoader(BaseballCardRepository baseballCardRepository,
        @Value("${terrypacker.baseball.card.data-file}") Resource cardDataFile) {
        this.cardRepository = baseballCardRepository;
        this.cardDataFile = cardDataFile;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //See if there are any cards?
            if (this.cardRepository.count().block() > 0L) {
                return;
            }
            Reader reader = new BufferedReader(
                new InputStreamReader(cardDataFile.getInputStream()));
            CSVReader csvReader = new CSVReader(reader);
            Iterator<ICommonsList<String>> it = csvReader.iterator();
            ICommonsList<String> headers = it.next();
            // Ensure the headers match:
            if (!"playerName".equals(headers.get(0)) ||
                !"teamName".equals(headers.get(1)) ||
                !"brand".equals(headers.get(2)) ||
                !"cardNumber".equals(headers.get(3)) ||
                !"year".equals(headers.get(4)) ||
                !"notes".equals(headers.get(5))
            ) {
                throw new RuntimeException("Invalid card file at " + cardDataFile);
            }
            while (it.hasNext()) {
                ICommonsList<String> row = it.next();
                if (row.size() != 6) {
                    throw new RuntimeException("Invalid format for staff file at" + cardDataFile);
                }
                BaseballCard card = BaseballCardBuilder.get()
                    .setPlayerName(row.get(0))
                    .setTeamName(row.get(1))
                    .setBrand(row.get(2))
                    .setCardNumber(Integer.parseInt(row.get(3)))
                    .setYear(Integer.parseInt(row.get(4)))
                    .setNotes(row.get(5)).build();
                this.cardRepository.save(card).block();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

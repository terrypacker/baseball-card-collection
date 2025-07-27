package com.terrypacker.cardcollection.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.entity.card.CardCsvMappingStrategy;
import com.terrypacker.cardcollection.entity.cardvalue.OwnedCardValue;
import com.terrypacker.cardcollection.entity.cardvalue.OwnedCardValueCsvMappingStrategy;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCardCsvMappingStrategy;
import com.terrypacker.cardcollection.entity.user.ApplicationUser;
import com.terrypacker.cardcollection.repository.cardcollection.CardRepository;
import com.terrypacker.cardcollection.repository.cardvalue.OwnedCardValueRepository;
import com.terrypacker.cardcollection.repository.ownedcard.OwnedCardRepository;
import com.terrypacker.cardcollection.repository.user.ApplicationUserRepository;
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
public class DefaultDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final Resource cardDataFile;
    private final CardRepository cardRepository;
    private final Resource ownedCardDataFile;
    private final OwnedCardRepository ownedCardRepository;
    private final Resource cardValueDataFile;
    private final OwnedCardValueRepository cardValueRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final Boolean loadCardData;

    public DefaultDataLoader(CardRepository cardRepository,
        @Value("${terrypacker.card-collection.data-loader.card.data-file}") Resource cardDataFile,
        OwnedCardRepository ownedCardRepository,
        @Value("${terrypacker.card-collection.data-loader.card.owned.data-file}") Resource ownedCardDataFile,
        OwnedCardValueRepository cardValueRepository,
        @Value("${terrypacker.card-collection.data-loader.card.value.data-file}") Resource cardValueDataFile,
        @Value("${terrypacker.card-collection.data-loader.load-card-data:false}") Boolean loadCardData,
        ApplicationUserRepository applicationUserRepository) {
        this.cardRepository = cardRepository;
        this.cardDataFile = cardDataFile;
        this.ownedCardRepository = ownedCardRepository;
        this.ownedCardDataFile = ownedCardDataFile;
        this.cardValueRepository = cardValueRepository;
        this.cardValueDataFile = cardValueDataFile;
        this.applicationUserRepository = applicationUserRepository;
        this.loadCardData = loadCardData;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //See if we need a default user
        Long userCount = this.applicationUserRepository.count().block();
        if(userCount == null || userCount <= 0L) {
            ApplicationUser user = new ApplicationUser();
            user.setUsername("admin");
            user.setPassword("{plain}admin");
            this.applicationUserRepository.save(user).block();
        }

        if(Boolean.TRUE.equals(loadCardData)) {
            try {
                //See if there are any cards?
                if (this.cardRepository.count().block() > 0L) {
                    return;
                }

                //Insert Cards from CSV
                try (Reader cardReader = new BufferedReader(
                    new InputStreamReader(cardDataFile.getInputStream()));) {
                    CsvToBean<Card> csvReader =
                        new CsvToBeanBuilder(cardReader).withMappingStrategy(
                                new CardCsvMappingStrategy())
                            .withSeparator(',').withIgnoreLeadingWhiteSpace(true)
                            .withIgnoreEmptyLine(true)
                            .build();

                    Iterator<Card> it = csvReader.iterator();
                    while (it.hasNext()) {
                        Card card = it.next();
                        card.setId(null);
                        this.cardRepository.save(card).block();
                    }
                }

                //Insert Owned from CSV
                try (Reader cardReader = new BufferedReader(
                    new InputStreamReader(ownedCardDataFile.getInputStream()));) {
                    CsvToBean<OwnedCard> csvReader =
                        new CsvToBeanBuilder(cardReader).withMappingStrategy(
                                new OwnedCardCsvMappingStrategy())
                            .withSeparator(',').withIgnoreLeadingWhiteSpace(true)
                            .withIgnoreEmptyLine(true)
                            .build();

                    Iterator<OwnedCard> it = csvReader.iterator();
                    while (it.hasNext()) {
                        OwnedCard card = it.next();
                        card.setId(null);
                        this.ownedCardRepository.save(card).block();
                    }
                }

                //Insert Values from CSV
                try (Reader cardReader = new BufferedReader(
                    new InputStreamReader(cardValueDataFile.getInputStream()));) {
                    CsvToBean<OwnedCardValue> csvReader =
                        new CsvToBeanBuilder(cardReader).withMappingStrategy(
                                new OwnedCardValueCsvMappingStrategy())
                            .withSeparator(',').withIgnoreLeadingWhiteSpace(true)
                            .withIgnoreEmptyLine(true)
                            .build();

                    Iterator<OwnedCardValue> it = csvReader.iterator();
                    while (it.hasNext()) {
                        OwnedCardValue value = it.next();
                        this.cardValueRepository.insert(value).block();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

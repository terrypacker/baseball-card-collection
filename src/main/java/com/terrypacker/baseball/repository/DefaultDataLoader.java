package com.terrypacker.baseball.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.entity.baseballcard.BaseballCardCsvMappingStrategy;
import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.entity.ownedcard.OwnedCardCsvMappingStrategy;
import com.terrypacker.baseball.entity.user.ApplicationUser;
import com.terrypacker.baseball.repository.baseballcard.BaseballCardRepository;
import com.terrypacker.baseball.repository.ownedcard.OwnedCardRepository;
import com.terrypacker.baseball.repository.user.ApplicationUserRepository;
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
    private final BaseballCardRepository cardRepository;
    private final Resource ownedCardDataFile;
    private final OwnedCardRepository ownedCardRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public DefaultDataLoader(BaseballCardRepository baseballCardRepository,
        @Value("${terrypacker.baseball.card.data-file}") Resource cardDataFile,
        OwnedCardRepository ownedCardRepository,
        @Value("${terrypacker.baseball.card.owned.data-file}") Resource ownedCardDataFile,
        ApplicationUserRepository applicationUserRepository) {
        this.cardRepository = baseballCardRepository;
        this.cardDataFile = cardDataFile;
        this.ownedCardRepository = ownedCardRepository;
        this.ownedCardDataFile = ownedCardDataFile;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //See if we need a defaul user
        if(this.applicationUserRepository.count().block() <= 0L) {
            ApplicationUser user = new ApplicationUser();
            user.setUsername("admin");
            user.setPassword("{plain}admin");
            this.applicationUserRepository.save(user).block();
        }

        try {
            //See if there are any cards?
            if (this.cardRepository.count().block() > 0L) {
                return;
            }

            //Insert Cards from CSV
            try (Reader cardReader = new BufferedReader(
                new InputStreamReader(cardDataFile.getInputStream()));) {
                CsvToBean<BaseballCard> csvReader =
                    new CsvToBeanBuilder(cardReader).withMappingStrategy(new BaseballCardCsvMappingStrategy())
                        .withSeparator(',').withIgnoreLeadingWhiteSpace(true).withIgnoreEmptyLine(true)
                        .build();

                Iterator<BaseballCard> it = csvReader.iterator();
                while (it.hasNext()) {
                    BaseballCard card = it.next();
                    card.setId(null);
                    this.cardRepository.save(card).block();
                }
            }

            //Insert Owned from CSV
            try (Reader cardReader = new BufferedReader(
                new InputStreamReader(ownedCardDataFile.getInputStream()));) {
                CsvToBean<OwnedCard> csvReader =
                    new CsvToBeanBuilder(cardReader).withMappingStrategy(new OwnedCardCsvMappingStrategy())
                        .withSeparator(',').withIgnoreLeadingWhiteSpace(true).withIgnoreEmptyLine(true)
                        .build();

                Iterator<OwnedCard> it = csvReader.iterator();
                while (it.hasNext()) {
                    OwnedCard card = it.next();
                    card.setId(null);
                    this.ownedCardRepository.save(card).block();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

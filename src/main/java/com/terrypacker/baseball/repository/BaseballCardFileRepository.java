package com.terrypacker.baseball.repository;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.csv.CSVReader;
import com.terrypacker.baseball.entity.BaseballCard;
import com.terrypacker.baseball.entity.BaseballCardBuilder;
import com.terrypacker.baseball.ui.collection.BaseballCardFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Left here for reference
 */
public class BaseballCardFileRepository implements BaseballCardRepository {

    private final List<BaseballCard> baseballCards = new ArrayList<>();

    public BaseballCardFileRepository(
        @Value("${terrypacker.baseball.card.data-file}") String cardsCsv) {
        try {
            Reader reader = new BufferedReader(new FileReader(Path.of(cardsCsv).toFile()));
            CSVReader csvReader = new CSVReader(reader);
            Iterator<ICommonsList<String>> it = csvReader.iterator();
            ICommonsList<String> headers = it.next();
            // Ensure the headers match:
            if (!"id".equals(headers.get(0)) ||
                !"playerName".equals(headers.get(1)) ||
                !"teamName".equals(headers.get(2)) ||
                !"brand".equals(headers.get(3)) ||
                !"cardNumber".equals(headers.get(4)) ||
                !"year".equals(headers.get(5)) ||
                !"notes".equals(headers.get(6))
            ) {
                throw new RuntimeException("Invalid card file at " + cardsCsv);
            }
            while (it.hasNext()) {
                ICommonsList<String> row = it.next();
                if (row.size() != 7) {
                    throw new RuntimeException("Invalid format for staff file at" + cardsCsv);
                }
                BaseballCard card = BaseballCardBuilder.get()
                    .setId(Integer.parseInt(row.get(0)))
                    .setPlayerName(row.get(1))
                    .setTeamName(row.get(2))
                    .setBrand(row.get(3))
                    .setCardNumber(Integer.parseInt(row.get(4)))
                    .setYear(Integer.parseInt(row.get(5)))
                    .setNotes(row.get(6)).build();
                this.baseballCards.add(card);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Flux<BaseballCard> findAll(Sort sort) {
        //TODO Add sorting
        return Flux.fromStream(baseballCards.stream());
    }

    @Override
    public <S extends BaseballCard> Mono<S> save(S entity) {
        //TODO Validation
        this.baseballCards.add(entity);
        return Mono.justOrEmpty(entity);
    }

    @Override
    public <S extends BaseballCard> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends BaseballCard> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<BaseballCard> findById(Integer integer) {
        return Mono.justOrEmpty(baseballCards.stream().filter(baseballCard -> {
            return baseballCard.getId().equals(integer);
        }).findFirst());
    }

    @Override
    public Mono<BaseballCard> findById(Publisher<Integer> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Integer integer) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<Integer> id) {
        return null;
    }

    @Override
    public Flux<BaseballCard> findAll() {
        return Flux.fromStream(baseballCards.stream());
    }

    @Override
    public Flux<BaseballCard> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public Flux<BaseballCard> findAllById(Publisher<Integer> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return Mono.just((long) baseballCards.size());
    }

    @Override
    public Mono<Void> deleteById(Integer integer) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<Integer> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(BaseballCard entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends Integer> integers) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends BaseballCard> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends BaseballCard> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }

    @Override
    public Stream<BaseballCard> query(Optional<BaseballCardFilter> filter,
        Sort sort, int limit, int offset) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public int countQuery(Optional<BaseballCardFilter> filter) {
        throw new RuntimeException("Not implemented yet");
    }
}

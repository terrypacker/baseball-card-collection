package com.terrypacker.cardcollection.repository.collection;

import com.terrypacker.cardcollection.entity.collection.CardInCollection;
import java.util.Optional;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CardInCollectionJooqRepository implements
    CardInCollectionRepository {

    @Override
    public Mono<CardInCollection> findByExample(CardInCollectionRecordFilter filter) {
        return null;
    }

    @Override
    public Stream<CardInCollection> query(Optional<CardInCollectionRecordFilter> entityFilter,
        Sort sort, int limit, int offset) {
        return Stream.empty();
    }

    @Override
    public int countQuery(Optional<CardInCollectionRecordFilter> optionalFilter) {
        return 0;
    }

    @Override
    public <S extends CardInCollection> Mono<S> save(S entity) {
        return null;
    }

    @Override
    public <S extends CardInCollection> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends CardInCollection> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<CardInCollection> findById(Integer integer) {
        return null;
    }

    @Override
    public Mono<CardInCollection> findById(Publisher<Integer> id) {
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
    public Flux<CardInCollection> findAll() {
        return null;
    }

    @Override
    public Flux<CardInCollection> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public Flux<CardInCollection> findAllById(Publisher<Integer> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
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
    public Mono<Void> delete(CardInCollection entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends Integer> integers) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends CardInCollection> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends CardInCollection> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }

    @Override
    public Flux<CardInCollection> findAll(Sort sort) {
        return null;
    }
}

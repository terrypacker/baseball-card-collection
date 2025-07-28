package com.terrypacker.cardcollection.repository.collection;

import com.terrypacker.cardcollection.db.tables.Collectorcard;
import com.terrypacker.cardcollection.db.tables.Ownedcard;
import com.terrypacker.cardcollection.db.tables.records.CollectorcardRecord;
import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.entity.card.CollectorCardBuilder;
import com.terrypacker.cardcollection.entity.card.Sport;
import com.terrypacker.cardcollection.entity.collection.CardInCollection;
import com.terrypacker.cardcollection.entity.collection.CardInCollectionBuilder;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.repository.Filter;
import com.terrypacker.cardcollection.repository.ownedcard.OwnedCardRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.impl.DSL;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CardInCollectionJooqRepository implements
    CardInCollectionRepository {
    protected final DSLContext create;
    private final OwnedCardRepository ownedCardRepository;
    protected final Collectorcard collectorcard;
    protected final Ownedcard ownedcard;

    public CardInCollectionJooqRepository(DSLContext dslContext,
        OwnedCardRepository ownedCardRepository) {
        this.create = dslContext;
        this.ownedCardRepository = ownedCardRepository;
        this.collectorcard = Collectorcard.COLLECTORCARD;
        this.ownedcard = Ownedcard.OWNEDCARD;
    }

    @Override
    public Mono<CardInCollection> findByExample(CardInCollectionRecordFilter filter) {
        Condition condition = DSL.trueCondition();
        for (Filter f : filter.getFilters()) {
            if (f.hasValue()) {
                condition = addAndCondition(f, condition);
            }
        }
        Select<CollectorcardRecord> select = create.selectFrom(collectorcard)
            .where(condition);
         Optional<CollectorCard> card = this.create.fetchOptional(select).map(this::unmapCollectorCardFromRecord);
         if(card.isPresent()) {
             //TODO add owned cards
             return Mono.just(CardInCollectionBuilder.get()
                 .setCard(card.get())
                 .build());
         }else {
             return Mono.empty();
         }
    }

    @Override
    public Stream<CardInCollection> query(Optional<CardInCollectionRecordFilter> entityFilter,
        Sort sort, int limit, int offset) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public int countQuery(Optional<CardInCollectionRecordFilter> optionalFilter) {
        Condition condition = DSL.trueCondition();
        if (optionalFilter.isPresent()) {
            CardInCollectionRecordFilter filter = optionalFilter.get();
            for (Filter f : filter.getFilters()) {
                if (f.hasValue()) {
                    condition = addAndCondition(f, condition);
                }
            }
        }
        return this.create.fetchCount(collectorcard, condition);
    }

    @Override
    public <S extends CardInCollection> Mono<S> save(S entity) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <S extends CardInCollection> Flux<S> saveAll(Iterable<S> entities) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <S extends CardInCollection> Flux<S> saveAll(Publisher<S> entityStream) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<CardInCollection> findById(Integer integer) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<CardInCollection> findById(Publisher<Integer> id) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Boolean> existsById(Integer integer) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Boolean> existsById(Publisher<Integer> id) {
        return null;
    }

    @Override
    public Flux<CardInCollection> findAll() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Flux<CardInCollection> findAllById(Iterable<Integer> integers) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Flux<CardInCollection> findAllById(Publisher<Integer> idStream) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Long> count() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> deleteById(Integer integer) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> deleteById(Publisher<Integer> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(CardInCollection entity) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends Integer> integers) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends CardInCollection> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends CardInCollection> entityStream) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> deleteAll() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Flux<CardInCollection> findAll(Sort sort) {
        throw new RuntimeException("Not implemented yet");
    }

    protected Condition addAndCondition(Filter<?> f, Condition condition) {
        return condition.and(f.getCondition());
    }

    protected CollectorCard unmapCollectorCardFromRecord(CollectorcardRecord r) {
        Flux<OwnedCard> ownedCards = ownedCardRepository.getOwnedCardsByCollectorCardId(r.getId());
        return CollectorCardBuilder.get()
            .setId(r.getId())
            .setSport(Sport.valueOf(r.getSport().toUpperCase(java.util.Locale.ROOT)))
            .setPlayerName(r.getPlayer())
            .setTeamName(r.getTeam())
            .setBrand(r.getBrand())
            .setCardNumber(r.getCardnumber())
            .setYear(r.getYear())
            .setNotes(r.getNotes())
            .setOwnedCards(ownedCards.collectList().block())
            .build();
    }

    protected CollectorcardRecord mapCollectorCardToRecord(CollectorCard entity) {
        CollectorcardRecord r = new CollectorcardRecord();
        r.setId(entity.getId());
        r.setSport(entity.getSport().name().toLowerCase(java.util.Locale.ROOT));
        r.setPlayer(entity.getPlayerName());
        r.setTeam(entity.getTeamName());
        r.setBrand(entity.getBrand());
        r.setCardnumber(entity.getCardNumber());
        r.setYear(entity.getYear());
        r.setNotes(entity.getNotes());
        return r;
    }
}

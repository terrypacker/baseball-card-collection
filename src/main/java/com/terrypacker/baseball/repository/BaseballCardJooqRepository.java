package com.terrypacker.baseball.repository;

import com.terrypacker.baseball.db.tables.Baseballcard;
import com.terrypacker.baseball.db.tables.records.BaseballcardRecord;
import com.terrypacker.baseball.entity.BaseballCard;
import com.terrypacker.baseball.entity.BaseballCardBuilder;
import com.terrypacker.baseball.ui.collection.BaseballCardFilter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Select;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BaseballCardJooqRepository extends JooqRepository implements
    BaseballCardRepository {

    public BaseballCardJooqRepository(DSLContext dslContext) {
        super(dslContext);
    }

    @Override
    public <S extends BaseballCard> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    private <S extends BaseballCard> Mono<S> insert(S entity) {
        return Mono.fromCallable(() -> {
            BaseballcardRecord record = mapToRecord(entity);
            record.changed(Baseballcard.BASEBALLCARD.ID, false);
            Integer id = create.insertInto(Baseballcard.BASEBALLCARD).set(record).execute();
            entity.setId(id);
            return entity;
        });
    }

    private <S extends BaseballCard> Mono<S> update(S entity) {
        return Mono.fromCallable(() -> {
            int updated = create.update(Baseballcard.BASEBALLCARD)
                .set(mapToRecord(entity))
                .where(Baseballcard.BASEBALLCARD.ID.eq(entity.getId())).execute();
            if (updated > 0) {
                return entity;
            } else {
                return null;
            }
        });
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
    public Mono<BaseballCard> findById(Integer id) {
        return Mono.justOrEmpty(
            create.fetchOptional(Baseballcard.BASEBALLCARD, Baseballcard.BASEBALLCARD.ID.eq(id))
                .map(this::unmapFromRecord));
    }

    @Override
    public Mono<BaseballCard> findById(Publisher<Integer> id) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Boolean> existsById(Integer integer) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Boolean> existsById(Publisher<Integer> id) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Flux<BaseballCard> findAll() {
        return Flux.fromStream(
            create.fetchStream(Baseballcard.BASEBALLCARD).map(this::unmapFromRecord));
    }

    @Override
    public Flux<BaseballCard> findAllById(Iterable<Integer> ids) {
        Condition condition = DSL.trueCondition();
        ids.forEach(id -> {
            condition.and(Baseballcard.BASEBALLCARD.ID.eq(id));
        });
        return Flux.fromStream(
            create.fetchStream(Baseballcard.BASEBALLCARD, condition).map(this::unmapFromRecord));
    }

    @Override
    public Flux<BaseballCard> findAllById(Publisher<Integer> idStream) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Long> count() {
        return Mono.just((long) create.fetchCount(Baseballcard.BASEBALLCARD));
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        //TODO Use mono properly
        create.deleteFrom(Baseballcard.BASEBALLCARD)
            .where(Baseballcard.BASEBALLCARD.ID.eq(id))
            .execute();
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(Publisher<Integer> publisher) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> delete(BaseballCard entity) {
        return deleteById(entity.getId());
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends Integer> ids) {
        //TODO Use mono properly
        Mono<Void> result = Mono.empty();
        Condition condition = DSL.trueCondition();
        ids.forEach(id -> {
            condition.and(Baseballcard.BASEBALLCARD.ID.eq(id));
        });
        create.deleteFrom(Baseballcard.BASEBALLCARD).where(condition).execute();
        return result;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends BaseballCard> entities) {
        //TODO Use mono properly
        Mono<Void> result = Mono.empty();
        entities.forEach(entity -> {
            delete(entity);
        });
        return result;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends BaseballCard> entityStream) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> deleteAll() {
        //TODO Use mono properly
        Mono<Void> result = Mono.empty();
        create.deleteFrom(Baseballcard.BASEBALLCARD).execute();
        return result;
    }

    @Override
    public Flux<BaseballCard> findAll(Sort sort) {
        throw new RuntimeException("Not implemented yet");
    }


    //TODO Change to Flux
    @Override
    public Stream<BaseballCard> query(Optional<BaseballCardFilter> filter,
        Sort sort, int limit, int offset) {
        Condition condition = DSL.trueCondition();
        if (filter.isPresent()) {
            BaseballCardFilter baseballCardFilter = filter.get();
            for (Filter f : baseballCardFilter.getFilters()) {
                if (f.hasValue()) {
                    condition = addAndCondition(f, condition);
                }
            }
        }
        Select<BaseballcardRecord> select = create.selectFrom(Baseballcard.BASEBALLCARD)
            .where(condition).orderBy(getOrderBy(sort)).limit(limit).offset(offset);
        return this.create.fetchStream(select).map(this::unmapFromRecord);
    }

    private List<SortField<?>> getOrderBy(Sort sort) {
        if(sort.isUnsorted()) {
            return Collections.emptyList();
        }else {
            return sort.get().map(so -> {
                Field<?> field = Baseballcard.BASEBALLCARD.field(so.getProperty());
                return so.getDirection() == Direction.ASC ? field.asc() : field.desc();
            }).collect(Collectors.toList());
        }
    }

    private Condition addAndCondition(Filter<?> f, Condition condition) {
        return condition.and(f.getCondition());
    }


    @Override
    public int countQuery(Optional<BaseballCardFilter> filter) {
        Condition condition = DSL.trueCondition();
        if (filter.isPresent()) {
            BaseballCardFilter baseballCardFilter = filter.get();
            for (Filter f : baseballCardFilter.getFilters()) {
                if (f.hasValue()) {
                    condition = addAndCondition(f, condition);
                }
            }
        }
        return this.create.fetchCount(Baseballcard.BASEBALLCARD, condition);
    }

    protected BaseballCard unmapFromRecord(BaseballcardRecord record) {
        return BaseballCardBuilder.get()
            .setId(record.getId())
            .setPlayerName(record.getPlayer())
            .setTeamName(record.getTeam())
            .setBrand(record.getBrand())
            .setCardNumber(record.getCardnumber())
            .setYear(record.getYear())
            .setNotes(record.getNotes())
            .build();
    }

    protected BaseballcardRecord mapToRecord(BaseballCard entity) {
        BaseballcardRecord record = new BaseballcardRecord();
        record.setId(entity.getId());
        record.setPlayer(entity.getPlayerName());
        record.setTeam(entity.getTeamName());
        record.setBrand(entity.getBrand());
        record.setCardnumber(entity.getCardNumber());
        record.setYear(entity.getYear());
        record.setNotes(entity.getNotes());
        return record;
    }

}

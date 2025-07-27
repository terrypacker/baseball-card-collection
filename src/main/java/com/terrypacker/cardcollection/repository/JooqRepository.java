package com.terrypacker.cardcollection.repository;

import com.terrypacker.cardcollection.entity.IdEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @param <T>
 * @param <E> - model used to transfer record out to service
 */
public abstract class JooqRepository<T extends TableImpl<R>, E extends IdEntity, R extends Record, F extends EntityFilter> implements
    ReactiveSortingRepository<E, Integer>,
    ReactiveCrudRepository<E, Integer> {

    protected final DSLContext create;
    protected final T table;

    public JooqRepository(DSLContext dslContext, T table) {
        this.create = dslContext;
        this.table = table;
    }


    public Mono<E> findById(Integer id) {
        return Mono.justOrEmpty(
            create.fetchOptional(table, getIdField().eq(id))
                .map(this::unmapFromRecord));
    }

    /**
     * Helper to insert if id == null else update via id
     * @param entity
     * @return
     * @param <S>
     */
    public <S extends E> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    protected <S extends E> Mono<S> insert(S entity) {
        return Mono.fromCallable(() -> {
            R record = mapToRecord(entity);
            record.changed(getIdField(), false);
            Integer id = create.insertInto(table).set(record).execute();
            entity.setId(id);
            return entity;
        });
    }

    protected <S extends E> Mono<S> update(S entity) {
        return Mono.fromCallable(() -> {
            int updated = create.update(table)
                .set(mapToRecord(entity))
                .where(getIdField().eq(entity.getId())).execute();
            if (updated > 0) {
                return entity;
            } else {
                return null;
            }
        });
    }

    public Mono<Long> count() {
        return Mono.just((long) create.fetchCount(table));
    }

    @Override
    public <S extends E> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends E> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<E> findById(Publisher<Integer> id) {
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
    public Flux<E> findAll() {
        return Flux.fromStream(
            create.fetchStream(table).map(this::unmapFromRecord));
    }

    @Override
    public Flux<E> findAllById(Iterable<Integer> ids) {
        Condition condition = DSL.trueCondition();
        ids.forEach(id -> {
            condition.and(getIdField().eq(id));
        });
        return Flux.fromStream(
            create.fetchStream(table, condition).map(this::unmapFromRecord));
    }

    @Override
    public Flux<E> findAllById(Publisher<Integer> idStream) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        //TODO Use mono properly
        create.deleteFrom(table)
            .where(getIdField().eq(id))
            .execute();
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(Publisher<Integer> publisher) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> delete(E entity) {
        return deleteById(entity.getId());
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends Integer> ids) {
        //TODO Use mono properly
        Mono<Void> result = Mono.empty();
        Condition condition = DSL.trueCondition();
        ids.forEach(id -> {
            condition.and(getIdField().eq(id));
        });
        create.deleteFrom(table).where(condition).execute();
        return result;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends E> entities) {
        //TODO Use mono properly
        Mono<Void> result = Mono.empty();
        entities.forEach(entity -> {
            delete(entity);
        });
        return result;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends E> entityStream) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Mono<Void> deleteAll() {
        //TODO Use mono properly
        Mono<Void> result = Mono.empty();
        create.deleteFrom(table).execute();
        return result;
    }

    @Override
    public Flux<E> findAll(Sort sort) {
        throw new RuntimeException("Not implemented yet");
    }

    //TODO Change to Flux
    //TODO Change to use Seek
    public Stream<E> query(Optional<F> entityFilter,
        Sort sort, int limit, int offset) {
        Condition condition = DSL.trueCondition();
        if (entityFilter.isPresent()) {
            F filter = entityFilter.get();
            for (Filter f : filter.getFilters()) {
                if (f.hasValue()) {
                    condition = addAndCondition(f, condition);
                }
            }
        }
        Select<R> select = create.selectFrom(table)
            .where(condition).orderBy(getOrderBy(sort)).limit(limit).offset(offset);
        return this.create.fetchStream(select).map(this::unmapFromRecord);
    }

    protected List<SortField<?>> getOrderBy(Sort sort) {
        if(sort.isUnsorted()) {
            return Collections.emptyList();
        }else {
            return sort.get().map(so -> {
                Field<?> field = table.field(so.getProperty());
                return so.getDirection() == Direction.ASC ? field.asc() : field.desc();
            }).collect(Collectors.toList());
        }
    }

    /**
     *
     * @param optionalFilter
     * @return
     */
    public int countQuery(Optional<F> optionalFilter) {
        Condition condition = DSL.trueCondition();
        if (optionalFilter.isPresent()) {
            F filter = optionalFilter.get();
            for (Filter f : filter.getFilters()) {
                if (f.hasValue()) {
                    condition = addAndCondition(f, condition);
                }
            }
        }
        return this.create.fetchCount(table, condition);
    }

    protected Condition addAndCondition(Filter<?> f, Condition condition) {
        return condition.and(f.getCondition());
    }

    protected abstract E unmapFromRecord(R record);
    protected abstract R mapToRecord(E entity);
    protected abstract Field<Integer> getIdField();

    public Mono<E> findByExample(F filter) {
        Condition condition = DSL.trueCondition();
        for (Filter f : filter.getFilters()) {
            if (f.hasValue()) {
                condition = addAndCondition(f, condition);
            }
        }
        Select<R> select = create.selectFrom(table)
            .where(condition);
        return Mono.justOrEmpty(this.create.fetchOptional(select).map(this::unmapFromRecord));
    }
}

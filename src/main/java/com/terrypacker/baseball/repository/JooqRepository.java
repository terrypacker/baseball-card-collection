package com.terrypacker.baseball.repository;

import com.terrypacker.baseball.entity.IdEntity;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import reactor.core.publisher.Mono;

/**
 *
 * @param <T>
 * @param <E> - model used to transfer record out to service
 */
public abstract class JooqRepository<T extends TableImpl<R>, E extends IdEntity, R extends Record> {

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

    public <S extends E> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    private <S extends E> Mono<S> insert(S entity) {
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

    protected abstract E unmapFromRecord(R record);
    protected abstract R mapToRecord(E entity);
    protected abstract Field<Integer> getIdField();
}

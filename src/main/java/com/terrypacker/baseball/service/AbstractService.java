package com.terrypacker.baseball.service;

import com.terrypacker.baseball.entity.IdEntity;
import com.terrypacker.baseball.repository.EntityFilter;
import com.terrypacker.baseball.repository.JooqRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
public class AbstractService<E extends IdEntity, R extends JooqRepository, F extends EntityFilter> {

    protected final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    public Flux<E> findAll() {
        return repository.findAll();
    }

    public Mono<E> save(E card) {
        return repository.save(card);
    }

    public Mono<E> findById(Integer id) {
        return repository.findById(id);
    }


    public Stream<E> query(Optional<F> filter,
        Sort sort, int limit, int offset) {
        return repository.query(filter, sort, limit, offset);
    }

    public int count(Optional<F> filter) {
        return repository.countQuery(filter);
    }
}

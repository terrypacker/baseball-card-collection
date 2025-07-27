package com.terrypacker.cardcollection.repository;

import com.terrypacker.cardcollection.entity.IdEntity;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface ReactiveRepository<E extends IdEntity, F extends EntityFilter> extends
    ReactiveSortingRepository<E, Integer>,
    ReactiveCrudRepository<E, Integer> {

    Mono<E> findByExample(F filter);
    Stream<E> query(Optional<F> entityFilter,
        Sort sort, int limit, int offset);
    int countQuery(Optional<F> optionalFilter);
}

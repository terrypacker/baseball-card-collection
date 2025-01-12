package com.terrypacker.baseball.service;

import com.terrypacker.baseball.entity.BaseballCard;
import com.terrypacker.baseball.repository.BaseballCardRepository;
import com.terrypacker.baseball.ui.collection.BaseballCardFilter;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BaseballCardService {

    private final BaseballCardRepository baseballCardRepository;

    public BaseballCardService(BaseballCardRepository baseballCardRepository) {
        this.baseballCardRepository = baseballCardRepository;
    }

    public Flux<BaseballCard> findAll() {
        return baseballCardRepository.findAll();
    }

    public Mono<BaseballCard> save(BaseballCard card) {
        return baseballCardRepository.save(card);
    }

    public Stream<BaseballCard> query(Optional<BaseballCardFilter> filter, int limit, int offset) {
        return baseballCardRepository.query(filter, limit, offset);
    }

    public int count(Optional<BaseballCardFilter> filter) {
        return baseballCardRepository.countQuery(filter);
    }
}

package com.terrypacker.baseball.service;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.repository.baseballcard.BaseballCardRepository;
import com.terrypacker.baseball.ui.view.collection.BaseballCardFilter;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Sort;
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

    public Stream<BaseballCard> query(Optional<BaseballCardFilter> filter,
        Sort sort, int limit, int offset) {
        return baseballCardRepository.query(filter, sort, limit, offset);
    }

    public int count(Optional<BaseballCardFilter> filter) {
        return baseballCardRepository.countQuery(filter);
    }

}

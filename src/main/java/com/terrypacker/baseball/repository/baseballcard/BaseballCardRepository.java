package com.terrypacker.baseball.repository.baseballcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface BaseballCardRepository extends ReactiveSortingRepository<BaseballCard, Integer>,
    ReactiveCrudRepository<BaseballCard, Integer> {
}

package com.terrypacker.baseball.repository;

import com.terrypacker.baseball.entity.BaseballCard;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseballCardRepository extends ReactiveSortingRepository<BaseballCard, Integer>, ReactiveCrudRepository<BaseballCard, Integer> {

}

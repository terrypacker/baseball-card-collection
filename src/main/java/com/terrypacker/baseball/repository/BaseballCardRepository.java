package com.terrypacker.baseball.repository;

import com.terrypacker.baseball.entity.BaseballCard;
import com.terrypacker.baseball.ui.collection.BaseballCardFilter;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface BaseballCardRepository extends ReactiveSortingRepository<BaseballCard, Integer>,
    ReactiveCrudRepository<BaseballCard, Integer> {


  /**
   * TODO Fix to use Seek
   * @param filter
   * @param limit
   * @param offset
   * @return
   */
    Stream<BaseballCard> query(Optional<BaseballCardFilter> filter, int limit, int offset);

    int countQuery(Optional<BaseballCardFilter> filter);

}

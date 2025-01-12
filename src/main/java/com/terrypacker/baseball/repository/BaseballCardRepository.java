package com.terrypacker.baseball.repository;

import com.terrypacker.baseball.entity.BaseballCard;
import com.terrypacker.baseball.ui.collection.BaseballCardFilter;
import com.vaadin.flow.data.provider.QuerySortOrder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface BaseballCardRepository extends ReactiveSortingRepository<BaseballCard, Integer>,
    ReactiveCrudRepository<BaseballCard, Integer> {


  /**
   * TODO Fix to use Seek and remove dependency on Vaadin Sort Order
   * Spring Data has @See org.springframework.data.domain.Sort
   *
   * @param filter
   * @param sortOrders
   * @param limit
   * @param offset
   * @return
   */
    Stream<BaseballCard> query(Optional<BaseballCardFilter> filter, List<QuerySortOrder> sortOrders,
        int limit, int offset);

    int countQuery(Optional<BaseballCardFilter> filter);


}

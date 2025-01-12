package com.terrypacker.baseball.ui.collection;

import com.terrypacker.baseball.entity.BaseballCard;
import com.terrypacker.baseball.service.BaseballCardService;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class BaseballCardDataProvider extends
    AbstractBackEndDataProvider<BaseballCard, Void> implements
    ConfigurableFilterDataProvider<BaseballCard, Void, BaseballCardFilter> {

    private final BaseballCardService baseballCardService;
    private Optional<BaseballCardFilter> configuredFilter = Optional.empty();

    public BaseballCardDataProvider(BaseballCardService baseballCardService) {
        this.baseballCardService = baseballCardService;
    }


    @Override
    protected Stream<BaseballCard> fetchFromBackEnd(Query<BaseballCard, Void> query) {
        return baseballCardService.query(configuredFilter, convertSort(query.getSortOrders()), query.getLimit(), query.getOffset());
    }

    /**
     * TODO Move to base service
     * @param sortOrders
     * @return
     */
    protected Sort convertSort(List<QuerySortOrder> sortOrders) {
        Sort sort;
        if(sortOrders.size() == 0) {
            sort = Sort.unsorted();
        }else {
            List<Order> orders = new ArrayList<>(sortOrders.size());
            for (QuerySortOrder sortOrder : sortOrders) {
                orders.add(new Order(
                    sortOrder.getDirection() == SortDirection.ASCENDING ? Direction.ASC
                        : Direction.DESC, sortOrder.getSorted()));
            }
            sort = Sort.by(orders);
        }
        return sort;
    }

    @Override
    protected int sizeInBackEnd(Query<BaseballCard, Void> query) {
        return baseballCardService.count(configuredFilter);
    }

    @Override
    public void refreshAll() {
        super.refreshAll();
    }

    @Override
    public void refreshItem(BaseballCard item) {
        super.refreshItem(item);
    }

    @Override
    public void refreshItem(BaseballCard item, boolean refreshChildren) {
        super.refreshItem(item, refreshChildren);
    }

    @Override
    public void setFilter(BaseballCardFilter filter) {
        this.configuredFilter = Optional.ofNullable(filter);
        refreshAll();
    }


}

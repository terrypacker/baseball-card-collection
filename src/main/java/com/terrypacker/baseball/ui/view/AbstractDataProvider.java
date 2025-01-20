package com.terrypacker.baseball.ui.view;

import com.terrypacker.baseball.entity.IdEntity;
import com.terrypacker.baseball.repository.EntityFilter;
import com.terrypacker.baseball.service.AbstractService;
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

/**
 * Queryable filterable Data Provider for Grids etc.
 * @author Terry Packer
 */
public abstract class AbstractDataProvider<E extends IdEntity, F extends EntityFilter, S extends AbstractService> extends
    AbstractBackEndDataProvider<E, Void> implements
    ConfigurableFilterDataProvider<E, Void, F> {

    protected Optional<F> configuredFilter = Optional.empty();
    protected final S service;

    public AbstractDataProvider(S service) {
        this.service = service;
    }

    @Override
    protected Stream<E> fetchFromBackEnd(Query<E, Void> query) {
        return service.query(configuredFilter, convertSort(query.getSortOrders()), query.getLimit(), query.getOffset());
    }

    @Override
    protected int sizeInBackEnd(Query<E, Void> query) {
        return service.count(configuredFilter);
    }

    @Override
    public void refreshAll() {
        super.refreshAll();
    }

    @Override
    public void refreshItem(E item) {
        super.refreshItem(item);
    }

    @Override
    public void refreshItem(E item, boolean refreshChildren) {
        super.refreshItem(item, refreshChildren);
    }

    @Override
    public void setFilter(F filter) {
        this.configuredFilter = Optional.ofNullable(filter);
        refreshAll();
    }

    /**
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
}

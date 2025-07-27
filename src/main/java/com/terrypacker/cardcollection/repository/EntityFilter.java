package com.terrypacker.cardcollection.repository;

import com.terrypacker.cardcollection.repository.Filter.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public abstract class EntityFilter {
    protected final List<AbstractPropertyFilter> filters = new ArrayList<>();
    protected Consumer<AbstractPropertyFilter> filterSet;

    public List<AbstractPropertyFilter> getFilters() {
        return filters;
    }

    protected void filterSet(AbstractPropertyFilter filter) {
        if(filterSet != null) {
            filterSet.accept(filter);
        }
    }

    public void setMatcher(Matcher type) {
        for(AbstractPropertyFilter filter : this.filters) {
            filter.setMatcher(type);
        }
    }
}

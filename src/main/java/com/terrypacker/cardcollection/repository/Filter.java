package com.terrypacker.cardcollection.repository;

import org.jooq.Condition;
import org.jooq.Field;

/**
 * @author Terry Packer
 */
public interface Filter<T> {

    enum Matcher {
        EQUALS, CONTAINS, LESS_THAN, GREATER_THAN
    }

    /**
     * Gets the property, or nested property path, to filter by. For example {@code "name"} or
     * {@code "address.city"}.
     *
     * @return the JOOQ Field
     */
    Field<T> getField();

    /**
     * Gets the filter value to compare against.
     *
     * @return the filter value
     */
    T getFilterValue();

    /**
     * The matcher, or operator, to use when comparing the property value to the filter value.
     *
     * @return the matcher
     */
    Matcher getMatcher();

    default Condition getCondition() {
        switch (getMatcher()) {
            case EQUALS -> {
                return getField().eq(getFilterValue());
            }
            case CONTAINS -> {
                return getField().like("%" + getFilterValue() + "%");
            }
            case LESS_THAN -> {
                return getField().lt(getFilterValue());
            }
            case GREATER_THAN -> {
                return getField().gt(getFilterValue());
            }
            default -> throw new IllegalStateException("Unexpected value: " + getMatcher());
        }
    }

    /**
     * Is something set in this filter we can use
     *
     * @return
     */
    default boolean hasValue() {
        return getFilterValue() != null;
    }

}

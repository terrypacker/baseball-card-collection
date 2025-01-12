package com.terrypacker.baseball.repository;

import org.jooq.Field;

/**
 * @author Terry Packer
 */
public abstract class AbstractPropertyFilter<T> implements Filter<T> {

    private Field<T> field;
    private T filterValue;
    private Matcher matcher;

    @Override
    public Field<T> getField() {
        return field;
    }

    /**
     * Sets the property, or nested property path, to filter by.
     *
     * @param field the property name
     */
    public void setField(Field<T> field) {
        this.field = field;
    }

    /**
     * Gets the filter value to compare against. The filter value is always stored as a string, but
     * can represent multiple types of values using specific formats. For example, when filtering a
     * property of type {@code LocalDate}, the filter value could be {@code "2020-01-01"}. The
     * actual filter implementation is responsible for parsing the filter value into the correct
     * type to use for querying the underlying data layer.
     *
     * @return the filter value
     */
    public T getFilterValue() {
        return filterValue;
    }

    /**
     * Sets the filter value to compare against.
     *
     * @param filterValue the filter value
     */
    public void setFilterValue(T filterValue) {
        this.filterValue = filterValue;
    }

    /**
     * The matcher, or operator, to use when comparing the property value to the filter value.
     *
     * @return the matcher
     */
    public Matcher getMatcher() {
        return matcher;
    }

    /**
     * Sets the matcher, or operator, to use when comparing the property value to the filter value.
     *
     * @param type the matcher
     */
    public void setMatcher(Matcher type) {
        this.matcher = type;
    }

    @Override
    public String toString() {
        return "PropertyFilter [field=" + field + ", matcher="
            + matcher + ", filterValue=" + filterValue + "]";
    }
}

package com.terrypacker.baseball.repository;

import org.jooq.DSLContext;

public abstract class JooqRepository {

    protected final DSLContext create;

    public JooqRepository(DSLContext dslContext) {
        this.create = dslContext;
    }
}

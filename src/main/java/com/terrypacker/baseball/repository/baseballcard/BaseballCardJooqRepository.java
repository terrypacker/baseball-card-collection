package com.terrypacker.baseball.repository.baseballcard;

import com.terrypacker.baseball.db.tables.Baseballcard;
import com.terrypacker.baseball.db.tables.records.BaseballcardRecord;
import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.entity.baseballcard.BaseballCardBuilder;
import com.terrypacker.baseball.repository.JooqRepository;
import com.terrypacker.baseball.ui.view.baseballcard.BaseballCardFilter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

@Repository
public class BaseballCardJooqRepository extends
    JooqRepository<Baseballcard, BaseballCard, BaseballcardRecord, BaseballCardFilter> implements
    BaseballCardRepository {

    public BaseballCardJooqRepository(DSLContext dslContext) {
        super(dslContext, Baseballcard.BASEBALLCARD);
    }

    protected BaseballCard unmapFromRecord(BaseballcardRecord record) {
        return BaseballCardBuilder.get()
            .setId(record.getId())
            .setPlayerName(record.getPlayer())
            .setTeamName(record.getTeam())
            .setBrand(record.getBrand())
            .setCardNumber(record.getCardnumber())
            .setYear(record.getYear())
            .setNotes(record.getNotes())
            .build();
    }

    protected BaseballcardRecord mapToRecord(BaseballCard entity) {
        BaseballcardRecord record = new BaseballcardRecord();
        record.setId(entity.getId());
        record.setPlayer(entity.getPlayerName());
        record.setTeam(entity.getTeamName());
        record.setBrand(entity.getBrand());
        record.setCardnumber(entity.getCardNumber());
        record.setYear(entity.getYear());
        record.setNotes(entity.getNotes());
        return record;
    }

    @Override
    protected Field<Integer> getIdField() {
        return table.ID;
    }

}

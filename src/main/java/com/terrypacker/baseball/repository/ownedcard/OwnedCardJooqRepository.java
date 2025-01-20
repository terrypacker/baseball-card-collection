package com.terrypacker.baseball.repository.ownedcard;

import com.terrypacker.baseball.db.tables.Ownedcard;
import com.terrypacker.baseball.db.tables.records.OwnedcardRecord;
import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.entity.ownedcard.OwnedCardBuilder;
import com.terrypacker.baseball.repository.JooqRepository;
import com.terrypacker.baseball.ui.view.ownedcard.OwnedCardFilter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

/**
 * @author Terry Packer
 */
@Repository
public class OwnedCardJooqRepository extends
    JooqRepository<Ownedcard, OwnedCard, OwnedcardRecord, OwnedCardFilter> implements
    OwnedCardRepository {

    public OwnedCardJooqRepository(DSLContext dslContext) {
        super(dslContext, Ownedcard.OWNEDCARD);
    }

    @Override
    protected OwnedCard unmapFromRecord(OwnedcardRecord record) {
        return OwnedCardBuilder.get()
            .setId(record.getId())
            .setBaseballCardId(record.getBaseballcardid())
            .setCardIdentifier(record.getCardidentifier())
            .setNotes(record.getNotes())
            .build();
    }

    @Override
    protected OwnedcardRecord mapToRecord(OwnedCard entity) {
        OwnedcardRecord record = new OwnedcardRecord();
        record.setId(entity.getId());
        record.setBaseballcardid(entity.getBaseballCardId());
        record.setCardidentifier(entity.getCardIdentifier());
        record.setNotes(entity.getNotes());
        return record;
    }

    @Override
    protected Field<Integer> getIdField() {
        return Ownedcard.OWNEDCARD.ID;
    }
}

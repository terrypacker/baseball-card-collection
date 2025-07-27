package com.terrypacker.cardcollection.repository.ownedcard;

import com.terrypacker.cardcollection.db.tables.Ownedcard;
import com.terrypacker.cardcollection.db.tables.records.OwnedcardRecord;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCardBuilder;
import com.terrypacker.cardcollection.repository.JooqRepository;
import com.terrypacker.cardcollection.ui.view.ownedcard.OwnedCardFilter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

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
    public Flux<OwnedCard> getOwnedCardsByBaseballCardId(int baseballCardId) {
        return Flux.fromStream(
            create.fetchStream(table, table.BASEBALLCARDID.eq(baseballCardId)).map(this::unmapFromRecord));
    }

    @Override
    protected OwnedCard unmapFromRecord(OwnedcardRecord record) {
        return OwnedCardBuilder.get()
            .setId(record.getId())
            .setBaseballCardId(record.getBaseballcardid())
            .setCardIdentifier(record.getCardidentifier())
            .setLot(record.getLot())
            .setNotes(record.getNotes())
            .build();
    }

    @Override
    protected OwnedcardRecord mapToRecord(OwnedCard entity) {
        OwnedcardRecord record = new OwnedcardRecord();
        record.setId(entity.getId());
        record.setBaseballcardid(entity.getBaseballCardId());
        record.setCardidentifier(entity.getCardIdentifier());
        record.setLot(entity.getLot());
        record.setNotes(entity.getNotes());
        return record;
    }

    @Override
    protected Field<Integer> getIdField() {
        return Ownedcard.OWNEDCARD.ID;
    }
}

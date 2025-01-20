package com.terrypacker.baseball.repository.cardvalue;

import com.terrypacker.baseball.db.tables.Ownedcardvalue;
import com.terrypacker.baseball.db.tables.records.OwnedcardvalueRecord;
import com.terrypacker.baseball.entity.cardvalue.OwnedCardValue;
import com.terrypacker.baseball.entity.cardvalue.OwnedCardValueBuilder;
import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Select;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
@Repository
public class OwnedCardValueJooqRepository implements
    OwnedCardValueRepository {
    protected final DSLContext create;
    protected final Ownedcardvalue table;

    public OwnedCardValueJooqRepository(DSLContext create) {
        this.create = create;
        this.table = Ownedcardvalue.OWNEDCARDVALUE;
    }

    public Mono<OwnedCardValue> insert(OwnedCardValue ownedCardValue) {
        return Mono.fromCallable(() -> {
            OwnedcardvalueRecord record = mapToRecord(ownedCardValue);
            create.insertInto(table).set(record).execute();
            return ownedCardValue;
        });
    }

    /**
     * Get the most recent value for an owned card.
     * @param ownedCard
     * @return
     * @throws NoSuchElementException
     */
    public Mono<OwnedCardValue> getLatestValue(OwnedCard ownedCard) throws NoSuchElementException {
        //TODO Make this reactive
        Select<OwnedcardvalueRecord> select = create.selectFrom(table)
            .where(table.OWNEDCARDID.eq(ownedCard.getId())).orderBy(table.TIME.desc()).limit(1);
        Result<OwnedcardvalueRecord> result = select.fetch();
        if(result.isEmpty()) {
            return Mono.error(new NoSuchElementException("No value found for card"));
        }
        return Mono.just(this.unmapFromRecord(result.get(0)));
    }

    protected OwnedCardValue unmapFromRecord(OwnedcardvalueRecord record) {
        return OwnedCardValueBuilder.get()
            .setOwnedCardId(record.getOwnedcardid())
            .setGrade(record.getGrade())
            .setValueInCents(record.getValue())
            .setTimestamp(record.getTime().atZone(ZoneId.systemDefault()))
            .build();
    }


    protected OwnedcardvalueRecord mapToRecord(OwnedCardValue entity) {
        OwnedcardvalueRecord record = new OwnedcardvalueRecord();
        record.setOwnedcardid(entity.getOwnedCardId());
        record.setGrade(entity.getGrade());
        record.setValue(entity.getValueInCents());
        record.setTime(entity.getTimestamp().toLocalDateTime());
        return record;
    }
}

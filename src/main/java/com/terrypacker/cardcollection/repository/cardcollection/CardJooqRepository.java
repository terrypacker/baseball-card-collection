package com.terrypacker.cardcollection.repository.cardcollection;

import com.terrypacker.cardcollection.db.tables.Collectorcard;
import com.terrypacker.cardcollection.db.tables.records.CollectorcardRecord;
import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.entity.card.CollectorCardBuilder;
import com.terrypacker.cardcollection.entity.card.Sport;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.repository.JooqRepository;
import com.terrypacker.cardcollection.repository.ownedcard.OwnedCardRepository;
import com.terrypacker.cardcollection.ui.view.cardcollection.CardFilter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CardJooqRepository extends
    JooqRepository<Collectorcard, CollectorCard, CollectorcardRecord, CardFilter> implements
    CardRepository {

    private final OwnedCardRepository ownedCardRepository;

    public CardJooqRepository(DSLContext dslContext, OwnedCardRepository ownedCardRepository) {
        super(dslContext, Collectorcard.COLLECTORCARD);
        this.ownedCardRepository = ownedCardRepository;
    }

    protected CollectorCard unmapFromRecord(CollectorcardRecord r) {
        Flux<OwnedCard> ownedCards = ownedCardRepository.getOwnedCardsByCollectorCardId(r.getId());
        return CollectorCardBuilder.get()
            .setId(r.getId())
            .setSport(Sport.valueOf(r.getSport().toUpperCase(java.util.Locale.ROOT)))
            .setPlayerName(r.getPlayer())
            .setTeamName(r.getTeam())
            .setBrand(r.getBrand())
            .setCardNumber(r.getCardnumber())
            .setYear(r.getYear())
            .setNotes(r.getNotes())
            .setOwnedCards(ownedCards.collectList().block())
            .build();
    }

    protected CollectorcardRecord mapToRecord(CollectorCard entity) {
        CollectorcardRecord r = new CollectorcardRecord();
        r.setId(entity.getId());
        r.setSport(entity.getSport().name().toLowerCase(java.util.Locale.ROOT));
        r.setPlayer(entity.getPlayerName());
        r.setTeam(entity.getTeamName());
        r.setBrand(entity.getBrand());
        r.setCardnumber(entity.getCardNumber());
        r.setYear(entity.getYear());
        r.setNotes(entity.getNotes());
        return r;
    }

    @Override
    protected Field<Integer> getIdField() {
        return table.ID;
    }

}

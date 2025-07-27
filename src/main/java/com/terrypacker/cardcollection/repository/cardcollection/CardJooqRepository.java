package com.terrypacker.cardcollection.repository.cardcollection;

import com.terrypacker.cardcollection.db.tables.Baseballcard;
import com.terrypacker.cardcollection.db.tables.records.BaseballcardRecord;
import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.entity.card.CardBuilder;
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
    JooqRepository<Baseballcard, Card, BaseballcardRecord, CardFilter> implements
    CardRepository {

    private final OwnedCardRepository ownedCardRepository;

    public CardJooqRepository(DSLContext dslContext, OwnedCardRepository ownedCardRepository) {
        super(dslContext, Baseballcard.BASEBALLCARD);
        this.ownedCardRepository = ownedCardRepository;
    }

    protected Card unmapFromRecord(BaseballcardRecord r) {
        Flux<OwnedCard> ownedCards = ownedCardRepository.getOwnedCardsByBaseballCardId(r.getId());
        return CardBuilder.get()
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

    protected BaseballcardRecord mapToRecord(Card entity) {
        BaseballcardRecord r = new BaseballcardRecord();
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

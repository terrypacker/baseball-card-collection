package com.terrypacker.cardcollection.ui.view.ownedcard;

import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCardBuilder;
import com.terrypacker.cardcollection.service.CardService;
import com.terrypacker.cardcollection.service.OwnedCardService;
import com.terrypacker.cardcollection.ui.view.AbstractEntityEditor;
import com.terrypacker.cardcollection.ui.view.card.CardSelect;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.function.Consumer;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
public class OwnedCardEditor extends AbstractEntityEditor<OwnedCard, OwnedCardService> {

    private final CardService cardService;

    public OwnedCardEditor(OwnedCardService service, CardService cardService, Consumer<OwnedCard> consumer) {
        super(service, consumer);
        this.cardService = cardService;
        this.init();
    }

    protected void init() {
        //TODO Shall we use the same data provider here or supply the service?
        CardSelect cardSelect = new CardSelect(cardService);
        TextField cardIdentifier = new TextField("Card Identifier");
        TextField lot = new TextField("Lot");
        TextField notes = new TextField("Notes");

        add(cardSelect, cardIdentifier, lot, notes);
        setResponsiveSteps(
            // Use one column by default
            new FormLayout.ResponsiveStep("0", 1),
            // Use two columns, if layout's width exceeds 500px
            new FormLayout.ResponsiveStep("500px", 2));
        // Stretch the username field over 2 columns

        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> {
            Mono<OwnedCard> save = service.save(OwnedCardBuilder.get()
                .setBaseballCardId(cardSelect.getValue().getId())
                .setCardIdentifier(cardIdentifier.getValue())
                .setLot(lot.getValue())
                .setNotes(notes.getValue()).build());
            OwnedCard result = save.block();
            postSave.accept(result);
        });
        add(saveButton);
    }
}

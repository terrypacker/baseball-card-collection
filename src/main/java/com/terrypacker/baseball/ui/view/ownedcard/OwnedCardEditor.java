package com.terrypacker.baseball.ui.view.ownedcard;

import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.entity.ownedcard.OwnedCardBuilder;
import com.terrypacker.baseball.service.BaseballCardService;
import com.terrypacker.baseball.service.OwnedCardService;
import com.terrypacker.baseball.ui.view.AbstractEntityEditor;
import com.terrypacker.baseball.ui.view.baseballcard.BaseballCardSelect;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.function.Consumer;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
public class OwnedCardEditor extends AbstractEntityEditor<OwnedCard, OwnedCardService> {

    private final BaseballCardService baseballCardService;

    public OwnedCardEditor(OwnedCardService service, BaseballCardService baseballCardService, Consumer<OwnedCard> consumer) {
        super(service, consumer);
        this.baseballCardService = baseballCardService;
        this.init();
    }

    protected void init() {
        //TODO Shall we use the same data provider here or supply the service?
        BaseballCardSelect cardSelect = new BaseballCardSelect(baseballCardService);
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

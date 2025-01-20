package com.terrypacker.baseball.ui.view.ownedcard;

import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.service.OwnedCardService;
import com.terrypacker.baseball.ui.view.AbstractEntityEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.function.Consumer;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
public class OwnedCardEditor extends AbstractEntityEditor<OwnedCard, OwnedCardService> {

    public OwnedCardEditor(OwnedCardService service, Consumer<OwnedCard> consumer) {
        super(service, consumer);
    }

    @Override
    protected void init() {
        TextField cardIdentifier = new TextField("Card Identifier");
        TextField notes = new TextField("Notes");

        add(cardIdentifier, notes);
        setResponsiveSteps(
            // Use one column by default
            new FormLayout.ResponsiveStep("0", 1),
            // Use two columns, if layout's width exceeds 500px
            new FormLayout.ResponsiveStep("500px", 2));
        // Stretch the username field over 2 columns

        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> {
            OwnedCard card = new OwnedCard(null, null, cardIdentifier.getValue(), notes.getValue());
            Mono<OwnedCard> save = service.save(card);
            OwnedCard result = save.block();
            postSave.accept(result);
        });
        add(saveButton);
    }
}

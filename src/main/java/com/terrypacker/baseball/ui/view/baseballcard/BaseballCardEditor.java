package com.terrypacker.baseball.ui.view.baseballcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.service.BaseballCardService;
import com.terrypacker.baseball.ui.view.AbstractEntityEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
public class BaseballCardEditor extends AbstractEntityEditor<BaseballCard, BaseballCardService> {


    public BaseballCardEditor(BaseballCardService service,
        Consumer<BaseballCard> postSave) {
        super(service, postSave);
    }

    @Override
    protected void init() {
        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        List<Integer> selectableYears = IntStream
            .range(now.getYear() - 100, now.getYear() + 1).boxed()
            .collect(Collectors.toList());

        TextField playerName = new TextField("Player name");
        TextField teamName = new TextField("Team Name");
        TextField brand = new TextField("Brand");
        IntegerField cardNumber = new IntegerField("Card Number");
        ComboBox<Integer> year = new ComboBox<>("Year", selectableYears);
        TextField notes = new TextField("Notes");

        add(playerName, teamName, brand, cardNumber, year, notes);
        setResponsiveSteps(
            // Use one column by default
            new FormLayout.ResponsiveStep("0", 1),
            // Use two columns, if layout's width exceeds 500px
            new FormLayout.ResponsiveStep("500px", 2));
        // Stretch the username field over 2 columns
        setColspan(playerName, 2);

        Button addCardButton = new Button("Add Card");
        addCardButton.addClickListener(event -> {
            BaseballCard card = new BaseballCard(null, playerName.getValue(), teamName.getValue(),
                brand.getValue(), cardNumber.getValue(), year.getValue(), notes.getValue());
            Mono<BaseballCard> save = service.save(card);
            BaseballCard result = save.block();
            postSave.accept(result);
        });
        add(addCardButton);
    }
}

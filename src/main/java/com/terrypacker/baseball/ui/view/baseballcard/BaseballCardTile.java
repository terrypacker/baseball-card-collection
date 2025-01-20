package com.terrypacker.baseball.ui.view.baseballcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Terry Packer
 */
public class BaseballCardTile extends FormLayout {

    private final BaseballCard baseballCard;

    public BaseballCardTile(BaseballCard card) {
        this(card, null, false);
    }

    public BaseballCardTile(BaseballCard card, Consumer<BaseballCard> selectListener, boolean editable) {
        this.baseballCard = card;
        this.getStyle().set("border", "var(--lumo-contrast-20pct)");
        if(selectListener != null) {
            this.addSingleClickListener(e -> {
                selectListener.accept(baseballCard);
            });
        }

        if(editable) {
            TextField playerName = new TextField("Player name");
            playerName.setEnabled(editable);
            playerName.setValue(baseballCard.getPlayerName());

            TextField teamName = new TextField("Team Name");
            teamName.setEnabled(editable);
            teamName.setValue(baseballCard.getTeamName());

            TextField brand = new TextField("Brand");
            brand.setEnabled(editable);
            brand.setValue(baseballCard.getBrand());

            IntegerField cardNumber = new IntegerField("Card Number");
            cardNumber.setEnabled(editable);
            cardNumber.setValue(baseballCard.getCardNumber());

            ComboBox<Integer> year = new ComboBox<>("Year");
            LocalDate now = LocalDate.now(ZoneId.systemDefault());
            List<Integer> selectableYears = IntStream
                .range(now.getYear() - 100, now.getYear() + 1).boxed()
                .collect(Collectors.toList());
            year.setItems(selectableYears);
            year.setValue(baseballCard.getYear());

            TextField notes = new TextField("Notes");
            notes.setValue(baseballCard.getNotes());

            add(playerName, teamName, brand, cardNumber, year, notes);

        }else {
            Span team = new Span("Team: " + baseballCard.getTeamName());
            Span brand = new Span("Brand: " + baseballCard.getBrand());
            Span cardNumber = new Span("Number: " + String.valueOf(baseballCard.getCardNumber()));
            Span year = new Span("Year: " + String.valueOf(baseballCard.getYear()));
            FormLayout content = new FormLayout(team, brand, cardNumber, year);
            content.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2));
            add(new Details(baseballCard.getPlayerName(), content));
        }

        setResponsiveSteps(
            // Use one column by default
            new FormLayout.ResponsiveStep("0", 1),
            // Use two columns, if layout's width exceeds 500px
            new FormLayout.ResponsiveStep("500px", 2));
    }
}

package com.terrypacker.cardcollection.ui.view.cardcollection;

import com.terrypacker.cardcollection.entity.card.Card;
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
public class CardTile extends FormLayout {

    private final Card card;

    public CardTile(Card card) {
        this(card, null, false);
    }

    public CardTile(Card card, Consumer<Card> selectListener, boolean editable) {
        this.card = card;
        this.getStyle().set("border", "var(--lumo-contrast-20pct)");
        if(selectListener != null) {
            this.addSingleClickListener(e -> {
                selectListener.accept(this.card);
            });
        }

        if(editable) {
            TextField playerName = new TextField("Player name");
            playerName.setEnabled(editable);
            playerName.setValue(this.card.getPlayerName());

            TextField teamName = new TextField("Team Name");
            teamName.setEnabled(editable);
            teamName.setValue(this.card.getTeamName());

            TextField brand = new TextField("Brand");
            brand.setEnabled(editable);
            brand.setValue(this.card.getBrand());

            IntegerField cardNumber = new IntegerField("Card Number");
            cardNumber.setEnabled(editable);
            cardNumber.setValue(this.card.getCardNumber());

            ComboBox<Integer> year = new ComboBox<>("Year");
            LocalDate now = LocalDate.now(ZoneId.systemDefault());
            List<Integer> selectableYears = IntStream
                .range(now.getYear() - 100, now.getYear() + 1).boxed()
                .collect(Collectors.toList());
            year.setItems(selectableYears);
            year.setValue(this.card.getYear());

            TextField notes = new TextField("Notes");
            notes.setValue(this.card.getNotes());

            add(playerName, teamName, brand, cardNumber, year, notes);

        }else {
            Span team = new Span("Team: " + this.card.getTeamName());
            Span brand = new Span("Brand: " + this.card.getBrand());
            Span cardNumber = new Span("Number: " + String.valueOf(this.card.getCardNumber()));
            Span year = new Span("Year: " + String.valueOf(this.card.getYear()));
            FormLayout content = new FormLayout(team, brand, cardNumber, year);
            content.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2));
            add(new Details(this.card.getPlayerName(), content));
        }

        setResponsiveSteps(
            // Use one column by default
            new FormLayout.ResponsiveStep("0", 1),
            // Use two columns, if layout's width exceeds 500px
            new FormLayout.ResponsiveStep("500px", 2));
    }
}

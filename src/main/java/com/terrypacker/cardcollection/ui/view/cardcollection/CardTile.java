package com.terrypacker.cardcollection.ui.view.cardcollection;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
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

    private final CollectorCard collectorCard;

    public CardTile(CollectorCard collectorCard) {
        this(collectorCard, null, false);
    }

    public CardTile(CollectorCard collectorCard, Consumer<CollectorCard> selectListener, boolean editable) {
        this.collectorCard = collectorCard;
        this.getStyle().set("border", "var(--lumo-contrast-20pct)");
        if(selectListener != null) {
            this.addSingleClickListener(e -> {
                selectListener.accept(this.collectorCard);
            });
        }

        if(editable) {
            TextField playerName = new TextField("Player name");
            playerName.setEnabled(editable);
            playerName.setValue(this.collectorCard.getPlayerName());

            TextField teamName = new TextField("Team Name");
            teamName.setEnabled(editable);
            teamName.setValue(this.collectorCard.getTeamName());

            TextField brand = new TextField("Brand");
            brand.setEnabled(editable);
            brand.setValue(this.collectorCard.getBrand());

            IntegerField cardNumber = new IntegerField("Card Number");
            cardNumber.setEnabled(editable);
            cardNumber.setValue(this.collectorCard.getCardNumber());

            ComboBox<Integer> year = new ComboBox<>("Year");
            LocalDate now = LocalDate.now(ZoneId.systemDefault());
            List<Integer> selectableYears = IntStream
                .range(now.getYear() - 100, now.getYear() + 1).boxed()
                .collect(Collectors.toList());
            year.setItems(selectableYears);
            year.setValue(this.collectorCard.getYear());

            TextField notes = new TextField("Notes");
            notes.setValue(this.collectorCard.getNotes());

            add(playerName, teamName, brand, cardNumber, year, notes);

        }else {
            Span team = new Span("Team: " + this.collectorCard.getTeamName());
            Span brand = new Span("Brand: " + this.collectorCard.getBrand());
            Span cardNumber = new Span("Number: " + String.valueOf(this.collectorCard.getCardNumber()));
            Span year = new Span("Year: " + String.valueOf(this.collectorCard.getYear()));
            FormLayout content = new FormLayout(team, brand, cardNumber, year);
            content.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2));
            add(new Details(this.collectorCard.getPlayerName(), content));
        }

        setResponsiveSteps(
            // Use one column by default
            new FormLayout.ResponsiveStep("0", 1),
            // Use two columns, if layout's width exceeds 500px
            new FormLayout.ResponsiveStep("500px", 2));
    }
}

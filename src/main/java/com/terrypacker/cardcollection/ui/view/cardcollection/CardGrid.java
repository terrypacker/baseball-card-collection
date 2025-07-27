package com.terrypacker.cardcollection.ui.view.cardcollection;

import com.terrypacker.cardcollection.entity.card.Card;
import com.terrypacker.cardcollection.ui.ValidationMessage;
import com.terrypacker.cardcollection.ui.view.AbstractFilteredGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Terry Packer
 */
public class CardGrid extends AbstractFilteredGrid<Card, CardFilter> {

    private CardDataProvider dataProvider;

    private Grid.Column<Card> sportColumn;
    private Grid.Column<Card> playerColumn;
    private Grid.Column<Card> teamColumn;
    private Grid.Column<Card> brandColumn;
    private Grid.Column<Card> cardNumberColumn;
    private Grid.Column<Card> yearColumn;
    private Grid.Column<Card> notesColumn;
    private Grid.Column<Card> ownedCardsColumn;

    public CardGrid(CardDataProvider dataProvider, Consumer<Card> onSelected) {
        super(dataProvider.withConfigurableFilter(), new CardFilter(dataProvider), onSelected);
        this.dataProvider = dataProvider;
        setupColumns();
        setupFiltering();
        setupEditing();
    }

    protected void setupColumns() {
        this.sportColumn = addColumn(c -> c.getSport().getLabel())
            .setHeader("Sport")
            .setSortable(true)
            .setSortProperty("sport");
        this.playerColumn = addColumn(Card::getPlayerName)
            .setHeader("Player")
            .setSortable(true)
            .setSortProperty("player");
        this.teamColumn = addColumn(Card::getTeamName)
            .setHeader("Team")
            .setSortable(true)
            .setSortProperty("team");
        this.brandColumn = addColumn(Card::getBrand)
            .setHeader("Brand")
            .setSortable(true)
            .setSortProperty("brand");
        this.cardNumberColumn = addColumn(Card::getCardNumber)
            .setHeader("Card Number")
            .setSortable(true)
            .setSortProperty("cardnumber");
        this.yearColumn = addColumn(Card::getYear)
            .setHeader("Year")
            .setSortable(true)
            .setSortProperty("year");
        this.notesColumn = addColumn(Card::getNotes)
            .setHeader("Notes")
            .setSortable(true)
            .setSortProperty("notes");
        this.ownedCardsColumn = addComponentColumn(c -> {
            return new Span(String.valueOf((int)c.getOwnedCards().stream().count()));
        }).setHeader("Owned Cards");
    }

    protected void setupFiltering() {
        this.filter = new CardFilter(dataProvider);
        getHeaderRows().clear();
        HeaderRow headerRow = appendHeaderRow();
        headerRow.getCell(sportColumn).setComponent(
            createFilterHeader("Sport", filter::setSport));
        headerRow.getCell(playerColumn).setComponent(
            createFilterHeader("Player", filter::setPlayerName));
        headerRow.getCell(teamColumn).setComponent(
            createFilterHeader("Team", filter::setTeamName));
        headerRow.getCell(brandColumn).setComponent(
            createFilterHeader("Brand", filter::setBrand));
        headerRow.getCell(cardNumberColumn).setComponent(
            createFilterHeader("Card Number", v -> {
                try {
                    filter.setCardNumber(Integer.parseInt(v));
                }catch(Exception e) {
                    filter.setCardNumber(null);
                }
            }));
        headerRow.getCell(yearColumn).setComponent(
            createFilterHeader("Year", (v) -> {
                try {
                    filter.setYear(Integer.parseInt(v));
                }catch(Exception e) {
                    filter.setYear(null);
                }
            }));
        headerRow.getCell(notesColumn).setComponent(
            createFilterHeader("Notes", filter::setNotes));
    }

    protected void setupEditing() {
        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        List<Integer> selectableYears = IntStream
            .range(now.getYear() - 100, now.getYear() + 1).boxed()
            .collect(Collectors.toList());

        Binder<Card> binder = new Binder<>(Card.class);
        Editor<Card> editor = getEditor();
        editor.setBinder(binder);

        TextField playerNameField = new TextField();
        playerNameField.setWidthFull();
        ValidationMessage playerNameValidationMessage = new ValidationMessage();
        addCloseHandler(playerNameField, editor);
        binder.forField(playerNameField)
            .asRequired("Player name must not be empty")
            .withStatusLabel(playerNameValidationMessage)
            .bind(Card::getPlayerName, Card::setPlayerName);
        playerColumn.setEditorComponent(playerNameField);

        TextField teamField = new TextField();
        teamField.setWidthFull();
        ValidationMessage teamValidationMessage = new ValidationMessage();
        addCloseHandler(teamField, editor);
        binder.forField(teamField)
            .asRequired("Team must not be empty")
            .withStatusLabel(teamValidationMessage)
            .bind(Card::getTeamName, Card::setTeamName);
        teamColumn.setEditorComponent(teamField);

        TextField brandField = new TextField();
        brandField.setWidthFull();
        ValidationMessage brandValidationMessage = new ValidationMessage();
        addCloseHandler(brandField, editor);
        binder.forField(brandField)
            .asRequired("Brand must not be empty")
            .withStatusLabel(brandValidationMessage)
            .bind(Card::getBrand, Card::setBrand);
        brandColumn.setEditorComponent(brandField);

        IntegerField cardNumberField = new IntegerField();
        cardNumberField.setWidthFull();
        ValidationMessage cardNumberValidationMessage = new ValidationMessage();
        addCloseHandler(cardNumberField, editor);
        binder.forField(cardNumberField)
            .asRequired("Card Number must not be empty")
            .withStatusLabel(cardNumberValidationMessage)
            .bind(Card::getCardNumber, Card::setCardNumber);
        cardNumberColumn.setEditorComponent(cardNumberField);

        ComboBox<Integer> yearField = new ComboBox<>();
        yearField.setItems(selectableYears);
        yearField.setWidthFull();
        ValidationMessage yearValidationMessage = new ValidationMessage();
        addCloseHandler(yearField, editor);
        binder.forField(yearField)
            .asRequired("Year must not be empty")
            .withStatusLabel(yearValidationMessage)
            .bind(Card::getYear, Card::setYear);
        yearColumn.setEditorComponent(yearField);

        TextField notesField = new TextField();
        notesField.setWidthFull();
        ValidationMessage notesValidationMessage = new ValidationMessage();
        addCloseHandler(notesField, editor);
        binder.forField(notesField)
            .withStatusLabel(notesValidationMessage)
            .bind(Card::getNotes, Card::setNotes);
        notesColumn.setEditorComponent(notesField);

        addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });
    }
}

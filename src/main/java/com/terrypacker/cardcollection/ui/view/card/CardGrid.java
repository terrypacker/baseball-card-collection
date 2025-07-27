package com.terrypacker.cardcollection.ui.view.card;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
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
public class CardGrid extends AbstractFilteredGrid<CollectorCard, CardFilter> {

    private CardDataProvider dataProvider;

    private Grid.Column<CollectorCard> sportColumn;
    private Grid.Column<CollectorCard> playerColumn;
    private Grid.Column<CollectorCard> teamColumn;
    private Grid.Column<CollectorCard> brandColumn;
    private Grid.Column<CollectorCard> cardNumberColumn;
    private Grid.Column<CollectorCard> yearColumn;
    private Grid.Column<CollectorCard> notesColumn;
    private Grid.Column<CollectorCard> ownedCardsColumn;

    public CardGrid(CardDataProvider dataProvider, Consumer<CollectorCard> onSelected) {
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
        this.playerColumn = addColumn(CollectorCard::getPlayerName)
            .setHeader("Player")
            .setSortable(true)
            .setSortProperty("player");
        this.teamColumn = addColumn(CollectorCard::getTeamName)
            .setHeader("Team")
            .setSortable(true)
            .setSortProperty("team");
        this.brandColumn = addColumn(CollectorCard::getBrand)
            .setHeader("Brand")
            .setSortable(true)
            .setSortProperty("brand");
        this.cardNumberColumn = addColumn(CollectorCard::getCardNumber)
            .setHeader("Card Number")
            .setSortable(true)
            .setSortProperty("cardnumber");
        this.yearColumn = addColumn(CollectorCard::getYear)
            .setHeader("Year")
            .setSortable(true)
            .setSortProperty("year");
        this.notesColumn = addColumn(CollectorCard::getNotes)
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

        Binder<CollectorCard> binder = new Binder<>(CollectorCard.class);
        Editor<CollectorCard> editor = getEditor();
        editor.setBinder(binder);

        TextField playerNameField = new TextField();
        playerNameField.setWidthFull();
        ValidationMessage playerNameValidationMessage = new ValidationMessage();
        addCloseHandler(playerNameField, editor);
        binder.forField(playerNameField)
            .asRequired("Player name must not be empty")
            .withStatusLabel(playerNameValidationMessage)
            .bind(CollectorCard::getPlayerName, CollectorCard::setPlayerName);
        playerColumn.setEditorComponent(playerNameField);

        TextField teamField = new TextField();
        teamField.setWidthFull();
        ValidationMessage teamValidationMessage = new ValidationMessage();
        addCloseHandler(teamField, editor);
        binder.forField(teamField)
            .asRequired("Team must not be empty")
            .withStatusLabel(teamValidationMessage)
            .bind(CollectorCard::getTeamName, CollectorCard::setTeamName);
        teamColumn.setEditorComponent(teamField);

        TextField brandField = new TextField();
        brandField.setWidthFull();
        ValidationMessage brandValidationMessage = new ValidationMessage();
        addCloseHandler(brandField, editor);
        binder.forField(brandField)
            .asRequired("Brand must not be empty")
            .withStatusLabel(brandValidationMessage)
            .bind(CollectorCard::getBrand, CollectorCard::setBrand);
        brandColumn.setEditorComponent(brandField);

        IntegerField cardNumberField = new IntegerField();
        cardNumberField.setWidthFull();
        ValidationMessage cardNumberValidationMessage = new ValidationMessage();
        addCloseHandler(cardNumberField, editor);
        binder.forField(cardNumberField)
            .asRequired("Card Number must not be empty")
            .withStatusLabel(cardNumberValidationMessage)
            .bind(CollectorCard::getCardNumber, CollectorCard::setCardNumber);
        cardNumberColumn.setEditorComponent(cardNumberField);

        ComboBox<Integer> yearField = new ComboBox<>();
        yearField.setItems(selectableYears);
        yearField.setWidthFull();
        ValidationMessage yearValidationMessage = new ValidationMessage();
        addCloseHandler(yearField, editor);
        binder.forField(yearField)
            .asRequired("Year must not be empty")
            .withStatusLabel(yearValidationMessage)
            .bind(CollectorCard::getYear, CollectorCard::setYear);
        yearColumn.setEditorComponent(yearField);

        TextField notesField = new TextField();
        notesField.setWidthFull();
        ValidationMessage notesValidationMessage = new ValidationMessage();
        addCloseHandler(notesField, editor);
        binder.forField(notesField)
            .withStatusLabel(notesValidationMessage)
            .bind(CollectorCard::getNotes, CollectorCard::setNotes);
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

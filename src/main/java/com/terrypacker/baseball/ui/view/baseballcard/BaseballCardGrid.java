package com.terrypacker.baseball.ui.view.baseballcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.ui.ValidationMessage;
import com.terrypacker.baseball.ui.view.AbstractFilteredGrid;
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
public class BaseballCardGrid extends AbstractFilteredGrid<BaseballCard, BaseballCardFilter> {

    private BaseballCardDataProvider dataProvider;

    private Grid.Column<BaseballCard> playerColumn;
    private Grid.Column<BaseballCard> teamColumn;
    private Grid.Column<BaseballCard> brandColumn;
    private Grid.Column<BaseballCard> cardNumberColumn;
    private Grid.Column<BaseballCard> yearColumn;
    private Grid.Column<BaseballCard> notesColumn;
    private Grid.Column<BaseballCard> ownedCardsColumn;

    public BaseballCardGrid(BaseballCardDataProvider dataProvider, Consumer<BaseballCard> onSelected) {
        super(dataProvider.withConfigurableFilter(), new BaseballCardFilter(dataProvider), onSelected);
        this.dataProvider = dataProvider;
        setupColumns();
        setupFiltering();
        setupEditing();
    }

    protected void setupColumns() {
        this.playerColumn = addColumn(BaseballCard::getPlayerName)
            .setHeader("Player")
            .setSortable(true)
            .setSortProperty("player");
        this.teamColumn = addColumn(BaseballCard::getTeamName)
            .setHeader("Team")
            .setSortable(true)
            .setSortProperty("team");
        this.brandColumn = addColumn(BaseballCard::getBrand)
            .setHeader("Brand")
            .setSortable(true)
            .setSortProperty("brand");
        this.cardNumberColumn = addColumn(BaseballCard::getCardNumber)
            .setHeader("Card Number")
            .setSortable(true)
            .setSortProperty("cardnumber");
        this.yearColumn = addColumn(BaseballCard::getYear)
            .setHeader("Year")
            .setSortable(true)
            .setSortProperty("year");
        this.notesColumn = addColumn(BaseballCard::getNotes)
            .setHeader("Notes")
            .setSortable(true)
            .setSortProperty("notes");
        this.ownedCardsColumn = addComponentColumn(c -> {
            return new Span(String.valueOf((int)c.getOwnedCards().stream().count()));
        }).setHeader("Owned Cards");
    }

    protected void setupFiltering() {
        this.filter = new BaseballCardFilter(dataProvider);
        getHeaderRows().clear();
        HeaderRow headerRow = appendHeaderRow();
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

        Binder<BaseballCard> binder = new Binder<>(BaseballCard.class);
        Editor<BaseballCard> editor = getEditor();
        editor.setBinder(binder);

        TextField playerNameField = new TextField();
        playerNameField.setWidthFull();
        ValidationMessage playerNameValidationMessage = new ValidationMessage();
        addCloseHandler(playerNameField, editor);
        binder.forField(playerNameField)
            .asRequired("Player name must not be empty")
            .withStatusLabel(playerNameValidationMessage)
            .bind(BaseballCard::getPlayerName, BaseballCard::setPlayerName);
        playerColumn.setEditorComponent(playerNameField);

        TextField teamField = new TextField();
        teamField.setWidthFull();
        ValidationMessage teamValidationMessage = new ValidationMessage();
        addCloseHandler(teamField, editor);
        binder.forField(teamField)
            .asRequired("Team must not be empty")
            .withStatusLabel(teamValidationMessage)
            .bind(BaseballCard::getTeamName, BaseballCard::setTeamName);
        teamColumn.setEditorComponent(teamField);

        TextField brandField = new TextField();
        brandField.setWidthFull();
        ValidationMessage brandValidationMessage = new ValidationMessage();
        addCloseHandler(brandField, editor);
        binder.forField(brandField)
            .asRequired("Brand must not be empty")
            .withStatusLabel(brandValidationMessage)
            .bind(BaseballCard::getBrand, BaseballCard::setBrand);
        brandColumn.setEditorComponent(brandField);

        IntegerField cardNumberField = new IntegerField();
        cardNumberField.setWidthFull();
        ValidationMessage cardNumberValidationMessage = new ValidationMessage();
        addCloseHandler(cardNumberField, editor);
        binder.forField(cardNumberField)
            .asRequired("Card Number must not be empty")
            .withStatusLabel(cardNumberValidationMessage)
            .bind(BaseballCard::getCardNumber, BaseballCard::setCardNumber);
        cardNumberColumn.setEditorComponent(cardNumberField);

        ComboBox<Integer> yearField = new ComboBox<>();
        yearField.setItems(selectableYears);
        yearField.setWidthFull();
        ValidationMessage yearValidationMessage = new ValidationMessage();
        addCloseHandler(yearField, editor);
        binder.forField(yearField)
            .asRequired("Year must not be empty")
            .withStatusLabel(yearValidationMessage)
            .bind(BaseballCard::getYear, BaseballCard::setYear);
        yearColumn.setEditorComponent(yearField);

        TextField notesField = new TextField();
        notesField.setWidthFull();
        ValidationMessage notesValidationMessage = new ValidationMessage();
        addCloseHandler(notesField, editor);
        binder.forField(notesField)
            .withStatusLabel(notesValidationMessage)
            .bind(BaseballCard::getNotes, BaseballCard::setNotes);
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

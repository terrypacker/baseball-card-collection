package com.terrypacker.baseball.ui.view.ownedcard;

import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.ui.ValidationMessage;
import com.terrypacker.baseball.ui.view.AbstractFilteredGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public class OwnedCardGrid extends AbstractFilteredGrid<OwnedCard, OwnedCardFilter> {

    private OwnedCardDataProvider dataProvider;

    private Grid.Column<OwnedCard> baseballCard;
    private Grid.Column<OwnedCard> cardIdentifierColumn;
    private Grid.Column<OwnedCard> notesColumn;

    public OwnedCardGrid(OwnedCardDataProvider dataProvider, Consumer<OwnedCard> onSelected) {
        super(dataProvider.withConfigurableFilter(), new OwnedCardFilter(dataProvider), onSelected);
        this.dataProvider = dataProvider;
        setupColumns();
        setupFiltering();
        setupEditing();
    }

    protected void setupColumns() {
        this.baseballCard = addColumn(OwnedCard::getBaseballCardId)
            .setHeader("Card")
            .setSortable(true)
            .setSortProperty("ownedCardId");
        this.cardIdentifierColumn = addColumn(OwnedCard::getCardIdentifier)
            .setHeader("Card Identifier")
            .setSortable(true)
            .setSortProperty("cardIdentifier");
        this.notesColumn = addColumn(OwnedCard::getNotes)
            .setHeader("Notes")
            .setSortable(true)
            .setSortProperty("notes");
    }

    protected void setupFiltering() {
        //Setup filtering
        getHeaderRows().clear();
        HeaderRow headerRow = appendHeaderRow();
        headerRow.getCell(baseballCard).setComponent(
            createFilterHeader("Card", null));
        headerRow.getCell(cardIdentifierColumn).setComponent(
            createFilterHeader("Card Identifier", filter::setCardIdentifier));
        headerRow.getCell(notesColumn).setComponent(
            createFilterHeader("Notes", filter::setNotes));
    }

    protected void setupEditing() {
        //Setup inline editing
        Binder<OwnedCard> binder = new Binder<>(OwnedCard.class);
        Editor<OwnedCard> editor = getEditor();
        editor.setBinder(binder);

        TextField cardIdentifierField = new TextField();
        cardIdentifierField.setWidthFull();
        ValidationMessage cardIdentifierValidationMessage = new ValidationMessage();
        addCloseHandler(cardIdentifierField, editor);
        binder.forField(cardIdentifierField)
            .asRequired("Must not be empty")
            .withStatusLabel(cardIdentifierValidationMessage)
            .bind(OwnedCard::getCardIdentifier, OwnedCard::setCardIdentifier);
        cardIdentifierColumn.setEditorComponent(cardIdentifierField);

        TextField notesField = new TextField();
        notesField.setWidthFull();
        ValidationMessage notesValidationMessage = new ValidationMessage();
        addCloseHandler(notesField, editor);
        binder.forField(notesField)
            .withStatusLabel(notesValidationMessage)
            .bind(OwnedCard::getNotes, OwnedCard::setNotes);
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

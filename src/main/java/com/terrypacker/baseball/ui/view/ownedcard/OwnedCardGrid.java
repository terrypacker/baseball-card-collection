package com.terrypacker.baseball.ui.view.ownedcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.entity.cardvalue.OwnedCardValue;
import com.terrypacker.baseball.entity.ownedcard.OwnedCard;
import com.terrypacker.baseball.service.BaseballCardService;
import com.terrypacker.baseball.service.OwnedCardValueService;
import com.terrypacker.baseball.ui.ValidationMessage;
import com.terrypacker.baseball.ui.view.AbstractFilteredGrid;
import com.terrypacker.baseball.ui.view.baseballcard.BaseballCardTile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
public class OwnedCardGrid extends AbstractFilteredGrid<OwnedCard, OwnedCardFilter> {

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    private OwnedCardDataProvider dataProvider;
    private OwnedCardValueService ownedCardValueService;
    private BaseballCardService baseballCardService;

    private Grid.Column<OwnedCard> baseballCard;
    private Grid.Column<OwnedCard> cardIdentifierColumn;
    private Grid.Column<OwnedCard> notesColumn;
    private Grid.Column<OwnedCard> latestValueColumn;

    public OwnedCardGrid(OwnedCardDataProvider dataProvider, Consumer<OwnedCard> onSelected,
        OwnedCardValueService ownedCardValueService,
        BaseballCardService baseballCardService) {
        super(dataProvider.withConfigurableFilter(), new OwnedCardFilter(dataProvider), onSelected);
        this.dataProvider = dataProvider;
        this.ownedCardValueService = ownedCardValueService;
        this.baseballCardService = baseballCardService;
        setupColumns();
        setupFiltering();
        setupEditing();
    }

    protected void setupColumns() {
        this.baseballCard = addComponentColumn(oc -> {
            BaseballCard card = baseballCardService.findById(oc.getBaseballCardId()).block();
            return new BaseballCardTile(card);
        })
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
        this.latestValueColumn = addComponentColumn(owned -> {
            try {
                Mono<OwnedCardValue> result = ownedCardValueService.getLatestOwnedCardValue(owned);
                OwnedCardValue ownedCardValue = result.block();
                return new Text(currencyFormat.format(ownedCardValue.getValueInCents() / 100D));
            }catch(NoSuchElementException e) {
                return new Text("No value found");
            }
        }).setHeader("Value").setSortable(false);
        /**
         * TODO add a latest value to the OwnedCard
        this.getListDataView().addItemCountChangeListener(event -> {
            this.getListDataView().getItems().mapToDouble()
            this.latestValueColumn.setFooter("Total: " + currencyFormat.format(totalValue));
        });
         **/
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

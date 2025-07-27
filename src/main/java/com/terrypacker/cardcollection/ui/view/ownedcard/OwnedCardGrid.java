package com.terrypacker.cardcollection.ui.view.ownedcard;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.terrypacker.cardcollection.entity.cardvalue.OwnedCardValue;
import com.terrypacker.cardcollection.entity.ownedcard.OwnedCard;
import com.terrypacker.cardcollection.service.CardService;
import com.terrypacker.cardcollection.service.OwnedCardValueService;
import com.terrypacker.cardcollection.service.ebay.EbayBrowseService;
import com.terrypacker.cardcollection.ui.ValidationMessage;
import com.terrypacker.cardcollection.ui.view.AbstractFilteredGrid;
import com.terrypacker.cardcollection.ui.view.card.CardTile;
import com.terrypacker.ebay.browse.models.ItemSummary;
import com.terrypacker.ebay.browse.models.SearchPagedCollection;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
public class OwnedCardGrid extends AbstractFilteredGrid<OwnedCard, OwnedCardFilter> {

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    private OwnedCardDataProvider dataProvider;
    private OwnedCardValueService ownedCardValueService;
    private CardService cardService;
    private EbayBrowseService ebayBrowseService;

    private Grid.Column<OwnedCard> baseballCard;
    private Grid.Column<OwnedCard> cardIdentifierColumn;
    private Grid.Column<OwnedCard> lotColumn;
    private Grid.Column<OwnedCard> notesColumn;
    private Grid.Column<OwnedCard> latestValueColumn;

    public OwnedCardGrid(OwnedCardDataProvider dataProvider, Consumer<OwnedCard> onSelected,
        OwnedCardValueService ownedCardValueService,
        CardService cardService,
        EbayBrowseService ebayBrowseService) {
        super(dataProvider.withConfigurableFilter(), new OwnedCardFilter(dataProvider), onSelected);
        this.dataProvider = dataProvider;
        this.ownedCardValueService = ownedCardValueService;
        this.cardService = cardService;
        this.ebayBrowseService = ebayBrowseService;
        setupColumns();
        setupFiltering();
        setupEditing();
    }

    protected void setupColumns() {
        this.baseballCard = addComponentColumn(oc -> {
            CollectorCard collectorCard = cardService.findById(oc.getCollectorCardId()).block();
            return new CardTile(collectorCard);
        })
            .setHeader("Card")
            .setSortable(true)
            .setSortProperty("baseballCardId");
        this.cardIdentifierColumn = addColumn(OwnedCard::getCardIdentifier)
            .setHeader("Card Identifier")
            .setSortable(true)
            .setSortProperty("cardIdentifier");
        this.lotColumn = addColumn(OwnedCard::getLot)
            .setHeader("Lot")
            .setSortable(true)
            .setSortProperty("lot");
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

        addComponentColumn( owned -> {
            Button getValueButton = new Button("Get Value");
            getValueButton.addClickListener(e -> {
                CollectorCard collectorCard = cardService.findById(owned.getCollectorCardId()).block();
                String query = collectorCard.getPlayerName() + " " + collectorCard.getYear() + " " + collectorCard.getBrand();
                try {
                    SearchPagedCollection result = this.ebayBrowseService.browse(query, 5, 0);
                    Dialog dialog = new Dialog("Results");
                    dialog.setWidth("50%");
                    Grid<ItemSummary> grid = new Grid<>(ItemSummary.class, false);
                    grid.addColumn(ItemSummary::getTitle).setHeader("Title");
                    Column<ItemSummary> priceColumn = grid.addColumn(summary -> {
                        String value = summary.getPrice().getValue();
                        return currencyFormat.format(Double.parseDouble(value));
                    }).setHeader("Price").setSortable(true).setSortProperty("price");
                    OptionalDouble avg = result.getItemSummaries().stream().mapToDouble(i -> Double.parseDouble(i.getPrice().getValue())).average();
                    priceColumn.setFooter("Avg: " + currencyFormat.format(avg.getAsDouble()));

                    grid.setItems(result.getItemSummaries());

                    dialog.add(grid);
                    dialog.open();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            return getValueButton;
        });

        //Add a total footer and make sure it stays in sync
        computeTotalFooter();
        this.getDataProvider().addDataProviderListener(event -> {
            computeTotalFooter();
        });
    }

    private void computeTotalFooter() {
        Double totalValue = this.getDataProvider().fetch(new Query<>()).mapToDouble(c -> {
            try {
                Mono<OwnedCardValue> result = ownedCardValueService.getLatestOwnedCardValue(c);
                OwnedCardValue ownedCardValue = result.block();
                return ownedCardValue.getValueInCents() / 100D;
            }catch(NoSuchElementException e) {
                return 0.0D;
            }
        }).reduce(0.0, Double::sum);
        this.latestValueColumn.setFooter("Current Value: " + currencyFormat.format(totalValue));
    }

    protected void setupFiltering() {
        //Setup filtering
        getHeaderRows().clear();
        HeaderRow headerRow = appendHeaderRow();
        headerRow.getCell(cardIdentifierColumn).setComponent(
            createFilterHeader("Card Identifier", filter::setCardIdentifier));
        headerRow.getCell(lotColumn).setComponent(
            createFilterHeader("Lot", filter::setLot));
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

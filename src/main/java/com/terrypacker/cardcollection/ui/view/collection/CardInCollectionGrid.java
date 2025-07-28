package com.terrypacker.cardcollection.ui.view.collection;

import com.terrypacker.cardcollection.entity.collection.CardInCollection;
import com.terrypacker.cardcollection.ui.view.AbstractFilteredGrid;
import com.terrypacker.cardcollection.ui.view.card.CardTile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.data.binder.Binder;
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public class CardInCollectionGrid extends AbstractFilteredGrid<CardInCollection, CardInCollectionFilter> {

    private CardInCollectionDataProvider dataProvider;

    private Column<CardInCollection> cardColumn;
    private Column<CardInCollection> sportColumn;
    private Column<CardInCollection> playerColumn;
    private Column<CardInCollection> teamColumn;
    private Column<CardInCollection> brandColumn;
    private Column<CardInCollection> cardNumberColumn;
    private Column<CardInCollection> yearColumn;
    private Column<CardInCollection> countColumn;

    public CardInCollectionGrid(CardInCollectionDataProvider dataProvider, Consumer<CardInCollection> onSelected) {
        super(dataProvider.withConfigurableFilter(), new CardInCollectionFilter(dataProvider), onSelected);
        this.dataProvider = dataProvider;
        setupColumns();
        setupFiltering();
        setupEditing();
    }

    protected void setupColumns() {
        this.cardColumn = addComponentColumn(oc -> new CardTile(oc.getCard()))
            .setHeader("Card")
            .setSortable(true)
            .setSortProperty("baseballCardId");
        this.sportColumn = addColumn(c -> c.getCard().getSport().getLabel())
            .setHeader("Sport")
            .setSortable(true)
            .setSortProperty("sport");
        this.playerColumn = addColumn(c -> c.getCard().getPlayerName())
            .setHeader("Player")
            .setSortable(true)
            .setSortProperty("player");
        this.teamColumn = addColumn(c -> c.getCard().getTeamName())
            .setHeader("Team")
            .setSortable(true)
            .setSortProperty("team");
        this.brandColumn = addColumn(c -> c.getCard().getBrand())
            .setHeader("Brand")
            .setSortable(true)
            .setSortProperty("brand");
        this.cardNumberColumn = addColumn(c -> c.getCard().getCardNumber())
            .setHeader("Card Number")
            .setSortable(true)
            .setSortProperty("cardnumber");
        this.yearColumn = addColumn(c -> c.getCard().getYear())
            .setHeader("Year")
            .setSortable(true)
            .setSortProperty("year");
        this.countColumn = addColumn(c -> c.getOwnedCards().size())
            .setHeader("Count")
            .setSortable(true)
            .setSortProperty("count");

    }

    protected void setupFiltering() {
        //Setup filtering
        getHeaderRows().clear();
        HeaderRow headerRow = appendHeaderRow();
        headerRow.getCell(sportColumn).setComponent(
            createFilterHeader("Sport", filter::setSport));
        headerRow.getCell(playerColumn).setComponent(
            createFilterHeader("Player", filter::setPlayerName));
        headerRow.getCell(teamColumn).setComponent(
            createFilterHeader("Team", filter::setTeamName));
    }

    protected void setupEditing() {
        //Setup inline editing
        Binder<CardInCollection> binder = new Binder<>(CardInCollection.class);
        Editor<CardInCollection> editor = getEditor();
        editor.setBinder(binder);

        addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });
    }
}

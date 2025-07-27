package com.terrypacker.cardcollection.ui.view.ownedcard;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.GridBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.grid.builder.RowBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.yaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.terrypacker.cardcollection.entity.cardvalue.OwnedCardValue;
import com.terrypacker.cardcollection.service.CardService;
import com.terrypacker.cardcollection.service.OwnedCardService;
import com.terrypacker.cardcollection.service.OwnedCardValueService;
import com.terrypacker.cardcollection.service.SecurityService;
import com.terrypacker.cardcollection.service.ebay.EbayBrowseService;
import com.terrypacker.cardcollection.ui.view.AbstractView;
import com.terrypacker.cardcollection.ui.view.ViewUtils;
import com.terrypacker.cardcollection.ui.view.card.CardDataProvider;
import com.terrypacker.cardcollection.ui.view.card.CardFilter;
import com.terrypacker.cardcollection.ui.view.card.CardVirtualList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import reactor.core.publisher.Flux;

/**
 * @author Terry Packer
 */
@PermitAll
@Route("/owned")
public class OwnedCardView extends AbstractView {

    public static final String TITLE = "Owned Cards";
    public static final String TAB_ID = "owned";

    private final OwnedCardService ownedCardService;
    private final OwnedCardValueService ownedCardValueService;
    private final CardService cardService;
    private final EbayBrowseService ebayBrowseService;

    @Autowired
    public OwnedCardView(
         OwnedCardService ownedCardService,
         OwnedCardViewDefinition ownedCardViewDefinition,
         SecurityService securityService,
         ViewUtils viewUtils,
         OwnedCardValueService ownedCardValueService,
         CardService cardService,
         EbayBrowseService ebayBrowseService) {
        super(ownedCardViewDefinition, securityService, viewUtils);
        this.ownedCardService = ownedCardService;
        this.ownedCardValueService = ownedCardValueService;
        this.cardService = cardService;
        this.ebayBrowseService = ebayBrowseService;
    }

    @PostConstruct
    public void init() {
        VerticalLayout layout = new VerticalLayout();
        displayOwnedCards(layout);
        setContent(layout);
    }

    private void displayOwnedCards(VerticalLayout layout) {
        Series<OwnedCardValueCoordinate> ownedCardValueSeries = new Series<>();
        ApexCharts chart = createLineChart();
        chart.setHeight("500px");
        OwnedCardDataProvider dataProvider = new OwnedCardDataProvider(
            ownedCardService);
        CardDataProvider cardDataProvider = new CardDataProvider(
            cardService);

        OwnedCardGrid ownedCardGrid = new OwnedCardGrid(dataProvider, c -> {
            Flux<OwnedCardValue> result = ownedCardValueService.getLatestValues(c, 100, Direction.ASC);
            List<OwnedCardValue> owned = result.collectList().block();
            List<OwnedCardValueCoordinate> coordinates = new ArrayList<>();
            for(OwnedCardValue ownedCardValue : owned) {
                coordinates.add(new OwnedCardValueCoordinate(ownedCardValue.getTimestamp(), ownedCardValue.getValueInCents()/100D));
            }
            populateChart(chart, new Series<>("Value", coordinates.toArray(new OwnedCardValueCoordinate[coordinates.size()])));
        }, ownedCardValueService, cardService, ebayBrowseService);

        CardVirtualList cardSelect = new CardVirtualList(cardDataProvider, selected -> {
            ownedCardGrid.getFilter().setCollectorCardId(selected.getId());
        });
        TextField nameFilter = new TextField("Search by name");
        CardFilter filter = new CardFilter(cardDataProvider);
        nameFilter.addValueChangeListener(event -> {
            filter.setPlayerName(event.getValue());
            cardDataProvider.setFilter(filter);
        });
        layout.add(nameFilter);
        layout.add(cardSelect);
        HorizontalLayout tableHeader = new HorizontalLayout();
        tableHeader.add(new H2("Owned Cards"));
        Button addButton = new Button("Add Owned Card");
        addButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Add Owned Card");
            OwnedCardEditor editor = new OwnedCardEditor(ownedCardService, cardService, oc -> {
                ownedCardGrid.getDataProvider().refreshItem(oc);
                dialog.close();
            });
            dialog.add(editor);
            dialog.setWidth("75%%");
            dialog.open();
        });
        tableHeader.add(addButton);
        layout.add(tableHeader);
        layout.add(ownedCardGrid);

        layout.add(chart);
    }

    private void populateChart(ApexCharts chart, Series<OwnedCardValueCoordinate> yValues) {
        chart.updateSeries(yValues);
    }

    private ApexCharts createLineChart() {
        return ApexChartsBuilder.get().withChart(
                ChartBuilder.get()
                    .withType(Type.LINE)
                    .withZoom(ZoomBuilder.get().withEnabled(false).build())
                    .build())
            .withStroke(StrokeBuilder.get()
                .withCurve(Curve.STRAIGHT)
                .build())
            .withGrid(GridBuilder.get()
                .withRow(RowBuilder.get()
                    .withColors("#f3f3f3", "transparent")
                    .withOpacity(0.5).build()
                ).build())
            .withDataLabels(DataLabelsBuilder.get()
                .withEnabled(false)
                .build()) //Add Grade into label
            .withSeries(new Series<>())
            .withXaxis(XAxisBuilder.get()
                .withLabels(com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder.get().withFormatter("function(val) {return val;}").build())
                .build())
            .withYaxis(YAxisBuilder.get()
                .withLabels(LabelsBuilder.get().withFormatter("function(val) {return new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(val);}").build()).build())
            .build();
    }

    private static class OwnedCardValueCoordinate extends Coordinate<String, Double> {
        public OwnedCardValueCoordinate(ZonedDateTime timestamp, Double value) {
            super(timestamp.format(DateTimeFormatter.ofPattern("d MMM uuuu")), value);
        }
    }
}

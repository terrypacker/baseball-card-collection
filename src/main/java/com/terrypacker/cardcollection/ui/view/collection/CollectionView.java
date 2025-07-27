package com.terrypacker.cardcollection.ui.view.collection;

import com.terrypacker.cardcollection.service.CardService;
import com.terrypacker.cardcollection.service.CollectionService;
import com.terrypacker.cardcollection.service.OwnedCardService;
import com.terrypacker.cardcollection.service.OwnedCardValueService;
import com.terrypacker.cardcollection.service.SecurityService;
import com.terrypacker.cardcollection.service.ebay.EbayBrowseService;
import com.terrypacker.cardcollection.ui.view.AbstractView;
import com.terrypacker.cardcollection.ui.view.ViewUtils;
import com.terrypacker.cardcollection.ui.view.cardcollection.CardDataProvider;
import com.terrypacker.cardcollection.ui.view.cardcollection.CardGrid;
import com.terrypacker.cardcollection.ui.view.ownedcard.OwnedCardDataProvider;
import com.terrypacker.cardcollection.ui.view.ownedcard.OwnedCardGrid;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Terry Packer
 */
@PermitAll
@Route("/collection")
public class CollectionView extends AbstractView {

    public static final String TITLE = "Collection";
    public static final String TAB_ID = "collection";

    private final CardService cardService;
    private final OwnedCardService ownedCardService;
    private final OwnedCardValueService ownedCardValueService;
    private final EbayBrowseService ebayBrowseService;
    private final CollectionService collectionService;

    @Autowired
    public CollectionView(
        CollectionViewDefinition collectionViewDefinition,
        ViewUtils viewUtils,
        SecurityService securityService,
        CardService cardService,
        OwnedCardService ownedCardService,
        OwnedCardValueService ownedCardValueService,
        EbayBrowseService ebayBrowseService,
        CollectionService collectionService) {
        super(collectionViewDefinition, securityService, viewUtils);
        this.cardService = cardService;
        this.ownedCardService = ownedCardService;
        this.ownedCardValueService = ownedCardValueService;
        this.ebayBrowseService = ebayBrowseService;
        this.collectionService = collectionService;
    }

    @PostConstruct
    public void init() {
        VerticalLayout layout = new VerticalLayout();
        displayCards(layout);
        setContent(layout);
    }

    private void displayCards(VerticalLayout layout) {

        H3 title = new H3("Collection");
        title.getStyle().set("margin", "0");
        HorizontalLayout header = new HorizontalLayout(title);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setHeight("var(--lumo-space-xl)");
        header.setFlexGrow(1, title);
        layout.add(header);

        OwnedCardDataProvider ownedCardDataProvider = new OwnedCardDataProvider(ownedCardService);
        OwnedCardGrid ownedCardGrid = new OwnedCardGrid(ownedCardDataProvider, c -> {},
            ownedCardValueService, cardService, ebayBrowseService);

        CardDataProvider cardDataProvider = new CardDataProvider(
            cardService);
        CardGrid grid = new CardGrid(cardDataProvider, card -> {
            ownedCardGrid.getFilter().setBaseballCardId(card.getId());
        });
        layout.add(grid);

        //TODO Move to contained component
        FormLayout form = new FormLayout();
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        form.add(upload);
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                BOMInputStream.builder().setInclude(false).setInputStream(inputStream).get(),
                StandardCharsets.UTF_8))){
                collectionService.importCards(reader,
                    cardInCollection -> {
                        System.out.println("Card in collection: " + cardInCollection);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        layout.add(form);
    }
}

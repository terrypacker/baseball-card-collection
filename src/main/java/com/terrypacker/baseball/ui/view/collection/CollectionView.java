package com.terrypacker.baseball.ui.view.collection;

import com.terrypacker.baseball.service.BaseballCardService;
import com.terrypacker.baseball.service.OwnedCardService;
import com.terrypacker.baseball.service.SecurityService;
import com.terrypacker.baseball.ui.view.BaseView;
import com.terrypacker.baseball.ui.view.baseballcard.BaseballCardDataProvider;
import com.terrypacker.baseball.ui.view.baseballcard.BaseballCardEditor;
import com.terrypacker.baseball.ui.view.baseballcard.BaseballCardGrid;
import com.terrypacker.baseball.ui.view.ownedcard.OwnedCardDataProvider;
import com.terrypacker.baseball.ui.view.ownedcard.OwnedCardGrid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Terry Packer
 */
@PermitAll
@Route("/collection")
public class CollectionView extends BaseView {

    public static final String TITLE = "Collection";
    public static final String TAB_ID = "collection";

    private final BaseballCardService baseballCardService;
    private final OwnedCardService ownedCardService;

    public CollectionView(
        @Autowired BaseballCardService baseballCardService, SecurityService securityService,
        OwnedCardService ownedCardService) {
        super(TITLE, TAB_ID, securityService);
        this.baseballCardService = baseballCardService;
        this.ownedCardService = ownedCardService;
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
        OwnedCardGrid ownedCardGrid = new OwnedCardGrid(ownedCardDataProvider, c -> {});

        BaseballCardDataProvider baseballCardDataProvider = new BaseballCardDataProvider(
            baseballCardService);
        BaseballCardGrid grid = new BaseballCardGrid(baseballCardDataProvider, card -> {
            ownedCardGrid.getFilter().setBaseballCardId(card.getId());
        });
        layout.add(grid);

        //Setup Owned Grid
        layout.add(ownedCardGrid);

        //Setup create new card
        layout.add(new Hr());
        BaseballCardEditor editor = new BaseballCardEditor(baseballCardService, c -> {
            grid.getDataProvider().refreshItem(c);
        });
        layout.add(editor);
    }
}

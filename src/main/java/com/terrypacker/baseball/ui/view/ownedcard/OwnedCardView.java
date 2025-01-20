package com.terrypacker.baseball.ui.view.ownedcard;

import com.terrypacker.baseball.service.OwnedCardService;
import com.terrypacker.baseball.service.SecurityService;
import com.terrypacker.baseball.ui.view.BaseView;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Terry Packer
 */
@PermitAll
@Route("/owned")
public class OwnedCardView extends BaseView {

    public static final String TITLE = "Owned Cards";
    public static final String TAB_ID = "owned";

    private final OwnedCardService ownedCardService;

    public OwnedCardView(
        @Autowired OwnedCardService ownedCardService, SecurityService securityService) {
        super(TITLE, TAB_ID, securityService);
        this.ownedCardService = ownedCardService;
    }

    @PostConstruct
    public void init() {
        VerticalLayout layout = new VerticalLayout();
        displayOwnedCards(layout);
        setContent(layout);
    }

    private void displayOwnedCards(VerticalLayout layout) {

        OwnedCardDataProvider dataProvider = new OwnedCardDataProvider(
            ownedCardService);
        OwnedCardGrid ownedCardGrid = new OwnedCardGrid(dataProvider, c -> {});
        layout.add(ownedCardGrid);

        //Setup create new card
        layout.add(new Hr());
        OwnedCardEditor editor = new OwnedCardEditor(ownedCardService, oc -> {
            ownedCardGrid.getDataProvider().refreshItem(oc);
        });
        layout.add(editor);
    }
}

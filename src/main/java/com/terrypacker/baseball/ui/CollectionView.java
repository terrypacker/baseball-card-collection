package com.terrypacker.baseball.ui;

import com.terrypacker.baseball.entity.BaseballCard;
import com.terrypacker.baseball.service.BaseballCardService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Terry Packer
 */
@Route("/collection")
public class CollectionView extends BaseView {
    public static final String TITLE = "Collection";
    public static final String TAB_ID = "collection";

    private final BaseballCardService baseballCardService;

    public CollectionView(
            @Autowired BaseballCardService baseballCardService) {
        super(TITLE, TAB_ID);
        this.baseballCardService = baseballCardService;
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

        Grid<BaseballCard> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        Grid.Column<BaseballCard> playerColumn = grid.addColumn(BaseballCard::getPlayerName).setHeader("Player").setSortable(true);
        Grid.Column<BaseballCard> teamColumn = grid.addColumn(BaseballCard::getTeamName).setHeader("Team").setSortable(true);
        Grid.Column<BaseballCard> brandColumn = grid.addColumn(BaseballCard::getBrand).setHeader("Brand").setSortable(true);
        Grid.Column<BaseballCard> cardNumberColumn = grid.addColumn(BaseballCard::getCardNumber).setHeader("Card Number").setSortable(true);
        Grid.Column<BaseballCard> yearColumn = grid.addColumn(BaseballCard::getYear).setHeader("Year").setSortable(true);
        Grid.Column<BaseballCard> notesColumn = grid.addColumn(BaseballCard::getNotes).setHeader("Notes").setSortable(true);

        Binder<BaseballCard> binder = new Binder<>(BaseballCard.class);
        Editor<BaseballCard> editor = grid.getEditor();
        editor.setBinder(binder);

        TextField firstNameField = new TextField();
        firstNameField.setWidthFull();
        ValidationMessage firstNameValidationMessage = new ValidationMessage();
        addCloseHandler(firstNameField, editor);
        binder.forField(firstNameField)
                .asRequired("Player name must not be empty")
                .withStatusLabel(firstNameValidationMessage)
                .bind(BaseballCard::getPlayerName, BaseballCard::setPlayerName);
        playerColumn.setEditorComponent(firstNameField);

        grid.setItems(baseballCardService.findAll().collectList().block());
        layout.add(grid);

        //Setup create new card
        layout.add(new Hr());
        FormLayout formLayout = new FormLayout();
        TextField playerName = new TextField("Player name");
        TextField teamName = new TextField("Team Name");
        TextField brand = new TextField("Brand");
        IntegerField cardNumber = new IntegerField("Card Number");
        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        List<Integer> selectableYears = IntStream
                .range(now.getYear() - 99, now.getYear() + 1).boxed()
                .collect(Collectors.toList());
        ComboBox<Integer> year = new ComboBox<>("Year", selectableYears);
        TextField notes = new TextField("Notes");

        formLayout.add(playerName, teamName, brand, cardNumber, year, notes);
        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2));
        // Stretch the username field over 2 columns
        formLayout.setColspan(playerName, 2);

        Button addCardButton = new Button("Add Card");
        addCardButton.addClickListener(event -> {
            BaseballCard card = new BaseballCard(null, playerName.getValue(), teamName.getValue(), brand.getValue(), cardNumber.getValue(), year.getValue(), notes.getValue());
            Mono<BaseballCard> save = baseballCardService.save(card);
            BaseballCard result = save.block();
            grid.getDataProvider().refreshItem(result);
        });
        formLayout.add(addCardButton);
        layout.add(formLayout);
    }

    private static void addCloseHandler(Component textField,
                                        Editor<BaseballCard> editor) {
        textField.getElement().addEventListener("keydown", e -> editor.cancel())
                .setFilter("event.code === 'Escape'");
    }
}

package com.terrypacker.baseball.ui.view.collection;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.service.BaseballCardService;
import com.terrypacker.baseball.service.SecurityService;
import com.terrypacker.baseball.ui.ValidationMessage;
import com.terrypacker.baseball.ui.view.BaseView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
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
import jakarta.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
@PermitAll
@Route("/collection")
public class CollectionView extends BaseView {

    public static final String TITLE = "Collection";
    public static final String TAB_ID = "collection";

    private final BaseballCardService baseballCardService;

    public CollectionView(
        @Autowired BaseballCardService baseballCardService, SecurityService securityService) {
        super(TITLE, TAB_ID, securityService);
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

        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        List<Integer> selectableYears = IntStream
            .range(now.getYear() - 100, now.getYear() + 1).boxed()
            .collect(Collectors.toList());

        BaseballCardDataProvider baseballCardDataProvider = new BaseballCardDataProvider(
            baseballCardService);
        Grid<BaseballCard> grid = new Grid<>(baseballCardDataProvider.withConfigurableFilter());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        Grid.Column<BaseballCard> playerColumn = grid.addColumn(BaseballCard::getPlayerName)
            .setHeader("Player")
            .setSortable(true)
            .setSortProperty("player");
        Grid.Column<BaseballCard> teamColumn = grid.addColumn(BaseballCard::getTeamName)
            .setHeader("Team")
            .setSortable(true)
            .setSortProperty("team");
        Grid.Column<BaseballCard> brandColumn = grid.addColumn(BaseballCard::getBrand)
            .setHeader("Brand")
            .setSortable(true)
            .setSortProperty("brand");
        Grid.Column<BaseballCard> cardNumberColumn = grid.addColumn(BaseballCard::getCardNumber)
            .setHeader("Card Number")
            .setSortable(true)
            .setSortProperty("cardnumber");
        Grid.Column<BaseballCard> yearColumn = grid.addColumn(BaseballCard::getYear)
            .setHeader("Year")
            .setSortable(true)
            .setSortProperty("year");
        Grid.Column<BaseballCard> notesColumn = grid.addColumn(BaseballCard::getNotes)
            .setHeader("Notes")
            .setSortable(true)
            .setSortProperty("notes");

        //Setup filtering
        BaseballCardFilter filter = new BaseballCardFilter(baseballCardDataProvider);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();
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

        //Setup inline editing
        Binder<BaseballCard> binder = new Binder<>(BaseballCard.class);
        Editor<BaseballCard> editor = grid.getEditor();
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

        grid.addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });

        layout.add(grid);

        //Setup create new card
        layout.add(new Hr());
        FormLayout formLayout = new FormLayout();
        TextField playerName = new TextField("Player name");
        TextField teamName = new TextField("Team Name");
        TextField brand = new TextField("Brand");
        IntegerField cardNumber = new IntegerField("Card Number");
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
            BaseballCard card = new BaseballCard(null, playerName.getValue(), teamName.getValue(),
                brand.getValue(), cardNumber.getValue(), year.getValue(), notes.getValue());
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

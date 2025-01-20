package com.terrypacker.baseball.ui.view;

import com.terrypacker.baseball.entity.IdEntity;
import com.terrypacker.baseball.repository.EntityFilter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public class AbstractFilteredGrid<E extends IdEntity, F extends EntityFilter> extends Grid<E> {

    protected F filter;

    public AbstractFilteredGrid(ConfigurableFilterDataProvider<E, Void, Void> dataProvider, F filter, Consumer<E> onSelected) {
        super(dataProvider);
        this.setSelectionMode(SelectionMode.SINGLE);
        this.addSelectionListener(selection -> {
            Optional<E> optionalCard = selection.getFirstSelectedItem();
            if (optionalCard.isPresent()) {
                onSelected.accept(optionalCard.get());
            }
        });
        this.filter = filter;
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }


    public F getFilter() {
        return filter;
    }

    protected Component createFilterHeader(String labelText,
        Consumer<String> filterChangeConsumer) {
        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
            .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
            e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    protected void addCloseHandler(Component textField,
        Editor<E> editor) {
        textField.getElement().addEventListener("keydown", e -> editor.cancel())
            .setFilter("event.code === 'Escape'");
    }
}

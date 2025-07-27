package com.terrypacker.cardcollection.ui.view;

import com.terrypacker.cardcollection.ui.ViewErrorHandler;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.StreamResource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class ViewUtils {

    private final List<ViewDefinition> views;
    private final ViewErrorHandler errorHandler;

    public ViewUtils(@Autowired List<ViewDefinition> views, ViewErrorHandler errorHandler) {
        this.views = views;
        this.errorHandler = errorHandler;
    }

    public Tab findMyTab(List<Tab> tabs, String tabId) {
        for (Tab t : tabs) {
            if (t.getId().isPresent() && t.getId().get().equals(tabId)) {
                return t;
            }
        }
        throw new RuntimeException("No tab defined for view");
    }

    public List<Tab> createMenuItems() {
        return views.stream().map(v -> {
            return createTab(v.getTabId(), v.getTitle(), v.getViewClass(), v.getIcon());
        }).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Helpful for Vaadin to convert an Iterator to a list
     * @param result
     * @return
     * @param <T>
     */
    public <T> List<T> convertToList(Iterator<T> result) {
        List<T> resultList = new ArrayList<>();
        while (result.hasNext()) {
            resultList.add(result.next());
        }
        return resultList;
    }

    /**
     * Create a tab component to put into the Application view
     * @param tabId
     * @param text
     * @param navigationTarget
     * @param icon
     * @return
     */
    public Tab createTab(String tabId, String text, Class<? extends com.vaadin.flow.component.Component> navigationTarget,
        VaadinIcon icon) {
        final Tab tab = new Tab(icon.create());
        tab.setId(tabId);
        tab.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    /**
     * Create a filter
     * @param labelText
     * @param filterChangeConsumer
     * @return
     */
    public VerticalLayout createFilterHeader(String labelText,
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

    public Tabs createMenu(String tabId) {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        List<Tab> allTabs = createMenuItems();
        Tab myTab = findMyTab(allTabs, tabId);
        tabs.add(allTabs.toArray(new Tab[allTabs.size()]));
        tabs.setSelectedTab(myTab);
        return tabs;
    }

    protected VerticalLayout createDrawerContent(Tabs menu, Image logo) {
        VerticalLayout layout = new VerticalLayout();

        // Configure styling for the drawer
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        // Have a drawer header with an application logo
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logo.setMaxHeight("50px");
        logoLayout.add(logo);

        // Display the logo and the menu in the drawer
        layout.add(logoLayout, menu);
        return layout;
    }

    public HorizontalLayout createHeaderContent(String viewTitle) {
        HorizontalLayout layout = new HorizontalLayout();

        // Configure styling for the header
        layout.setId("header");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Have the drawer toggle button on the left
        layout.add(new DrawerToggle());

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        layout.add(viewTitle);

        return layout;
    }

    /**
     * Create a download button to handle downloading content
     *
     * @param label - label for button
     * @param streamResource - resource to stream out on-click
     * @return Anchor to place in View
     */
    public Anchor createDownloadAnchorButton(String label, StreamResource streamResource) {
        Anchor anchor = new Anchor();
        anchor.getElement().setAttribute("download", true);
        Button downloadJsonButton = new Button(label);
        anchor.add(downloadJsonButton);
        anchor.setHref(streamResource);
        return anchor;
    }

    /**
     * Safely display a general error
     * @param event - event to display
     */
    public void displayError(ErrorEvent event) {
        this.errorHandler.error(event);
    }
}

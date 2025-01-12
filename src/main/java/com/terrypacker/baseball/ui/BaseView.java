package com.terrypacker.baseball.ui;

import com.terrypacker.baseball.ui.collection.CollectionView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
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
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public abstract class BaseView extends AppLayout {

    protected final Image logo = new Image("https://docs-v5.radixiot.com/img/logo.svg",
        "Mango Logo");
    protected final Tabs menu;
    protected final H3 viewTitle;
    protected final String tabId;

    protected BaseView(String viewTitle, String tabId) {
        this.tabId = tabId;
        this.viewTitle = new H3(viewTitle);
        setPrimarySection(Section.DRAWER);
        createHeaderContent();
        // Make the nav bar a header
        addToNavbar(true, createHeaderContent());

        this.menu = createMenu();
        addToDrawer(createDrawerContent(this.menu));

    }

    protected static Component createFilterHeader(String labelText,
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

    protected static Tab createTab(String tabId, String text,
        Class<? extends Component> navigationTarget,
        VaadinIcon icon) {
        final Tab tab = new Tab(icon.create());
        tab.setId(tabId);
        tab.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    protected Component createDrawerContent(Tabs menu) {
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

    protected Component createHeaderContent() {
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

    protected Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        Tab[] allTabs = createMenuItems();
        Tab myTab = findMyTab(allTabs);
        tabs.add(allTabs);
        tabs.setSelectedTab(myTab);
        return tabs;
    }

    protected Tab findMyTab(Tab[] tabs) {
        for (Tab t : tabs) {
            if (t.getId().isPresent() && t.getId().get().equals(tabId)) {
                return t;
            }
        }
        throw new RuntimeException("No tab defined for view");
    }

    protected Tab[] createMenuItems() {
        return new Tab[]{
            BaseView.createTab(CollectionView.TAB_ID, CollectionView.TITLE, CollectionView.class,
                VaadinIcon.DESKTOP),
            // TODO Add other views here
        };
    }
}

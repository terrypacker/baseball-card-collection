package com.terrypacker.baseball.ui.view;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.service.SecurityService;
import com.terrypacker.baseball.ui.view.collection.CollectionView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;

/**
 * @author Terry Packer
 */
public abstract class BaseView extends AppLayout {

    protected final H3 viewTitle;
    protected final String tabId;
    protected final SecurityService securityService;

    protected final Image logo = new Image("https://docs-v5.radixiot.com/img/logo.svg",
        "Mango Logo");
    protected final Tabs menu;

    protected BaseView(String viewTitle, String tabId, SecurityService securityService) {
        this.tabId = tabId;
        this.viewTitle = new H3(viewTitle);
        this.securityService = securityService;
        setPrimarySection(Section.DRAWER);
        createHeaderContent();
        // Make the nav bar a header
        addToNavbar(true, createHeaderContent());

        this.menu = createMenu();
        addToDrawer(createDrawerContent(this.menu));

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

        String username = securityService.getAuthenticatedUser().getUsername();
        Button logout = new Button("Log out " + username, e -> securityService.logout());

        // Display the logo and the menu in the drawer
        layout.add(logoLayout, menu, logout);
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
        };
    }

    private void addCloseHandler(Component textField,
        Editor<BaseballCard> editor) {
        textField.getElement().addEventListener("keydown", e -> editor.cancel())
            .setFilter("event.code === 'Escape'");
    }
}

package com.terrypacker.cardcollection.ui.view;

import com.terrypacker.cardcollection.service.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.StreamResource;

/**
 * @author Terry Packer
 */
public class AbstractView extends AppLayout {

    protected final Image logo = new Image(new StreamResource("mlb.png", () -> getClass().getResourceAsStream("/web/img/mlb.png")), "Baseball Cards");
    protected final ViewDefinition definition;
    protected final SecurityService securityService;
    protected final ViewUtils viewUtils;

    protected final Tabs menu;
    protected final TabSheet tabs;

    protected AbstractView(ViewDefinition definition, SecurityService securityService, ViewUtils viewUtils) {
        this.definition = definition;
        this.securityService = securityService;
        this.viewUtils = viewUtils;
        this.tabs = new TabSheet();
        this.menu = viewUtils.createMenu(this.definition.getTabId());

        setPrimarySection(Section.DRAWER);
        addToNavbar(true, viewUtils.createHeaderContent(this.definition.getTitle()));
        addToDrawer(viewUtils.createDrawerContent(this.menu, logo));

    }
}

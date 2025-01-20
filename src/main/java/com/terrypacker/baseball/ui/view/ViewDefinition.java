package com.terrypacker.baseball.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * @author Terry Packer
 */
public interface ViewDefinition {

    /**
     * The user readable title of the view
     * @return
     */
    String getTitle();

    /**
     * Unique id for this tab
     * @return
     */
    String getTabId();

    /**
     * Get the icon for the menu item
     * @return
     */
    VaadinIcon getIcon();

    /**
     * Get the implementation
     * @return
     */
    Class<? extends Component> getViewClass();

}

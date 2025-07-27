package com.terrypacker.cardcollection.ui.view.ownedcard;

import com.terrypacker.cardcollection.ui.view.ViewDefinition;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class OwnedCardViewDefinition implements ViewDefinition {

    @Override
    public String getTitle() {
        return "Owned Cards";
    }

    @Override
    public String getTabId() {
        return "owned-cards";
    }

    @Override
    public VaadinIcon getIcon() {
        return VaadinIcon.USER_CARD;
    }

    @Override
    public Class<? extends com.vaadin.flow.component.Component> getViewClass() {
        return OwnedCardView.class;
    }
}

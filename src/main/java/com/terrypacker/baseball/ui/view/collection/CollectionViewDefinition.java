package com.terrypacker.baseball.ui.view.collection;

import com.terrypacker.baseball.ui.view.ViewDefinition;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class CollectionViewDefinition implements ViewDefinition {

    @Override
    public String getTitle() {
        return "Collection";
    }

    @Override
    public String getTabId() {
        return "collection";
    }

    @Override
    public VaadinIcon getIcon() {
        return VaadinIcon.DESKTOP;
    }

    @Override
    public Class<? extends com.vaadin.flow.component.Component> getViewClass() {
        return CollectionView.class;
    }
}

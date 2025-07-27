package com.terrypacker.cardcollection.ui.view.cardcollection;

import com.terrypacker.cardcollection.entity.card.CollectorCard;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;

/**
 * @author Terry Packer
 */
public class CardRenderer extends ComponentRenderer<Component, CollectorCard> {


    public CardRenderer(
        SerializableFunction<CollectorCard, Component> componentFunction) {
        super(componentFunction);
    }
}

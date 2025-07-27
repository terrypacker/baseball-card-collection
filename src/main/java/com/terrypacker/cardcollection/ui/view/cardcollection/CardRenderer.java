package com.terrypacker.cardcollection.ui.view.cardcollection;

import com.terrypacker.cardcollection.entity.card.Card;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;

/**
 * @author Terry Packer
 */
public class CardRenderer extends ComponentRenderer<Component, Card> {


    public CardRenderer(
        SerializableFunction<Card, Component> componentFunction) {
        super(componentFunction);
    }
}

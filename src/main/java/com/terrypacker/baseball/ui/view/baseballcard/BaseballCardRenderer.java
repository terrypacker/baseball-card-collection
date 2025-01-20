package com.terrypacker.baseball.ui.view.baseballcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;

/**
 * @author Terry Packer
 */
public class BaseballCardRenderer extends ComponentRenderer<Component, BaseballCard> {


    public BaseballCardRenderer(
        SerializableFunction<BaseballCard, Component> componentFunction) {
        super(componentFunction);
    }
}

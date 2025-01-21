package com.terrypacker.baseball.ui.view;

import com.terrypacker.baseball.entity.IdEntity;
import com.terrypacker.baseball.service.AbstractService;
import com.vaadin.flow.component.formlayout.FormLayout;
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public abstract class AbstractEntityEditor<E extends IdEntity, S extends AbstractService> extends FormLayout {

    protected final S service;
    protected final Consumer<E> postSave;

    public AbstractEntityEditor(S service, Consumer<E> postSave) {
        this.service = service;
        this.postSave = postSave;
    }
}

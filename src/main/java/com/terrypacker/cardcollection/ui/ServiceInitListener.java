package com.terrypacker.cardcollection.ui;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class ServiceInitListener implements VaadinServiceInitListener {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInitListener.class);
    private final ViewErrorHandler viewErrorHandler;

    public ServiceInitListener(ViewErrorHandler viewErrorHandler) {
        this.viewErrorHandler = viewErrorHandler;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addSessionInitListener(
            sessionInitEvent -> {
                logger.debug("A new Session has been initialized!");
                sessionInitEvent.getSession().setErrorHandler(this.viewErrorHandler);
            });
        event.getSource().addUIInitListener(
            initEvent -> logger.debug("A new UI has been initialized!"));
    }
}

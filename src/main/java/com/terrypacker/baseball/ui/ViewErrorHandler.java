package com.terrypacker.baseball.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class ViewErrorHandler implements ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ViewErrorHandler.class);

    @Override
    public void error(ErrorEvent errorEvent) {
        logger.error("Uncaught error", errorEvent.getThrowable());
        if(UI.getCurrent() != null) {
            displayError(errorEvent.getThrowable());
        }
    }

    /**
     * Find the root cause and display the error message in a notification for 5s
     *
     * @param event to display root cause of error from
     */
    protected void displayError(@Nonnull Throwable event) {
        Throwable cause = event;
        for (Throwable t = event; t != null; t = t.getCause()) {
            if (t.getCause() == null) // We're at final cause
            {
                cause = t;
            }
        }
        if (UI.getCurrent() != null) {
            String localErrorMessage = cause.getLocalizedMessage();
            displayError(localErrorMessage);
        }
    }

    protected void displayError(@Nonnull String message) {
        UI.getCurrent().access(() -> {
            Notification notification = new Notification("Error: " + message, 5000, Notification.Position.BOTTOM_START);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        });
    }
}

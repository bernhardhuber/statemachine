/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import org.huberb.simplesm.model.Transition;
import org.huberb.simplesm.model.State;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author berni
 */
class LoggingNotificationStepImpl extends AbstractStepImpl implements Serializable {

    private static final long serialVersionUID = 20160831L;
    private static final Logger logger = Logger.getLogger(LoggingNotificationStepImpl.class.getName());

    private final INotifier notifier = (NotificationEvent notificationEvent) -> {
        logger.info(() -> "Notify: " + notificationEvent);
    };


    @Override
    protected void notify(NotificationEvent notificationEvent) {
        notifier.notify(notificationEvent);
    }

}

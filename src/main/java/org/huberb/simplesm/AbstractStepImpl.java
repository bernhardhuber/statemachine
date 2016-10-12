/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import org.huberb.simplesm.model.Transition;
import org.huberb.simplesm.model.State;
import java.io.Serializable;

/**
 *
 * @author berni
 */
public abstract class AbstractStepImpl implements IStep, Serializable {

    private static final long serialVersionUID = 20161012L;

    @Override
    public void stepFromTo(State from, Transition transition, State to) {
        if (from != null) {
            notify(new NotificationEvent.NotificationEventBuilder().stateOnexit(from).build());
        }
        if (transition != null) {
            notify(new NotificationEvent.NotificationEventBuilder().transition(transition).build());
        }
        if (to != null) {
            notify(new NotificationEvent.NotificationEventBuilder().stateOnentry(to).build());
        }
    }

    abstract void notify(NotificationEvent build);

}

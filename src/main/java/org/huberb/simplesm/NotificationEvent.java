/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import org.huberb.simplesm.model.Transition;
import org.huberb.simplesm.model.State;

/**
 *
 * @author berni
 */
public abstract class NotificationEvent {

    public enum NotificationType {

        onexit, transition, onentry
    }
    private final NotificationType notificationType;

    protected NotificationEvent(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public static class NotificationStateEvent extends NotificationEvent {

        private final String stateId;

        NotificationStateEvent(NotificationType notificationType, String stateId) {
            super(notificationType);
            this.stateId = stateId;
        }

        public String getStateId() {
            return stateId;
        }

        @Override
        public String toString() {
            final String result = new java.util.StringJoiner(",").
                    add("notificationType " + getNotificationType()).
                    add("stateId " + stateId).toString();
            return result;
        }
    }

    public static class NotificationTransitionEvent extends NotificationEvent {

        private final String eventName;

        NotificationTransitionEvent(NotificationType NotificationType, String eventName) {
            super(NotificationType);
            this.eventName = eventName;
        }

        public String getEventName() {
            return eventName;
        }

        @Override
        public String toString() {
            final String result = new java.util.StringJoiner(",").
                    add("notificationType " + getNotificationType()).
                    add("eventName " + eventName).toString();
            return result;
        }
    }

    static class NotificationEventBuilder {

        private NotificationEvent nf;

        NotificationEventBuilder() {
        }

        NotificationEventBuilder stateOnexit(State s) {
            nf = new NotificationStateEvent(NotificationEvent.NotificationType.onexit, s.getId());
            return this;
        }

        NotificationEventBuilder stateOnentry(State s) {
            nf = new NotificationStateEvent(NotificationEvent.NotificationType.onentry, s.getId());
            return this;
        }

        NotificationEventBuilder transition(Transition t) {
            nf = new NotificationStateEvent(NotificationEvent.NotificationType.transition, t.getEventName());
            return this;
        }

        NotificationEvent build() {
            return nf;
        }
    }
}

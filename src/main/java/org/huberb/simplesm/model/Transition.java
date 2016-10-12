/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm.model;

import java.io.Serializable;

/**
 *
 * @author berni
 */
public class Transition implements Serializable {

    private static final long serialVersionUID = 20160828L;
    private final State target;
    private final String eventName;

    Transition() {
        this(null, null);
    }

    public Transition(State target, String eventName) {
        this.target = target;
        this.eventName = eventName;
    }

    public State getTarget() {
        return target;
    }

    public String getEventName() {
        return eventName;
    }

}

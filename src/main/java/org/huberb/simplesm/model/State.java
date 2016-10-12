/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author berni
 */
public class State implements Serializable {

    private static final long serialVersionUID = 20160828L;
    private final String id;
    private final List<Transition> to;
    private final boolean finalState;

    State() {
        this(null, null, false);
    }

    public State(String id, List<Transition> to, boolean finalState) {
        this.id = id;
        this.to = to;
        this.finalState = finalState;
    }

    void addTransition(Transition transition) {
        this.to.add(transition);
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    Transition findTransition(String eventName) {
        Transition foundTransition = null;
        if (!this.finalState) {
            for (Transition transition : this.to) {
                if (eventName.equals(transition.getEventName())) {
                    foundTransition = transition;
                    break;
                }
            }
        }
        return foundTransition;
    }

    public boolean isFinalState() {
        return this.finalState;
    }

    public List<Transition> getTransitions() {
        return this.to;
    }
}

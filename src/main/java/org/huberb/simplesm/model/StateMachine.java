/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 *
 * @author berni
 */
public class StateMachine implements Serializable {

    private static final long serialVersionUID = 20160828L;
    private final Set<State> states = new HashSet();
    private State initalState;

    public State getInitalState() {
        return this.initalState;
    }

    void setInitalState(State initialState) {
        this.initalState = initialState;
    }

    void addState(State state) {
        this.states.add(state);
    }

    public Set<State> findAnyMatchingStates(String stateId) {
        Set<State> matchedStates = new HashSet<>();
        Predicate<State> p = (s) -> stateId.equals(s.getId());
        states.stream().filter((state) -> p.test(state)).
                forEach((state) -> {
                    matchedStates.add(state);
                });
        return Collections.unmodifiableSet(matchedStates);
    }

}

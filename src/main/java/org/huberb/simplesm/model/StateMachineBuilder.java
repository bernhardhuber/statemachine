/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.huberb.simplesm.model.StateMachineBuilder.StateMachineBuilderContext.StateNode;
import org.huberb.simplesm.model.StateMachineBuilder.StateMachineBuilderContext.TransitionNode;

/**
 *
 * @author berni
 */
public class StateMachineBuilder {

    static class StateMachineBuilderContext {

        static class StateNode {

            String id;
            boolean finalState;

            public StateNode(String id, boolean finalState) {
                this.id = id;
                this.finalState = finalState;
            }
        }

        static class TransitionNode {

            String eventName;
            String sourceStateId;
            String targetStateId;

            public TransitionNode(String eventName, String sourceStateId, String targetStateId) {
                this.eventName = eventName;
                this.sourceStateId = sourceStateId;
                this.targetStateId = targetStateId;
            }
        }
        List<StateNode> stateNodes = new ArrayList();
        List<TransitionNode> transitionNodes = new ArrayList();
    }

    private final StateMachine stateMachine = new StateMachine();
    private final StateMachineBuilderContext stateMachineBuilderContext = new StateMachineBuilderContext();
    private String lastStateId;
    private String initialStateId;

    public StateMachineBuilder initalstate(String id) {
        this.initialStateId = id;
        return this;
    }

    public StateMachineBuilder finalState(String id) {
        StateNode stateNode = new StateNode(id, true);
        stateMachineBuilderContext.stateNodes.add(stateNode);
        lastStateId = id;
        return this;
    }

    public StateMachineBuilder state(String id) {
        StateNode stateNode = new StateNode(id, false);
        stateMachineBuilderContext.stateNodes.add(stateNode);
        lastStateId = id;
        return this;
    }

    public StateMachineBuilder transition(String eventName, String targetStateId) {
        TransitionNode transitionNode = new TransitionNode(eventName, this.lastStateId, targetStateId);
        stateMachineBuilderContext.transitionNodes.add(transitionNode);
        return this;
    }

    public StateMachine build() {
        final Map<String, State> stateMap = new HashMap();
        //---
        for (StateNode stateNode : stateMachineBuilderContext.stateNodes) {
            final List<Transition> transitions = new ArrayList();
            final State state = new State(stateNode.id, transitions, stateNode.finalState);
            stateMachine.addState(state);
            stateMap.put(stateNode.id, state);
        }
        //---
        for (TransitionNode transitionNode : stateMachineBuilderContext.transitionNodes) {
            final State targetState = stateMap.get(transitionNode.targetStateId);
            final Transition transition = new Transition(targetState, transitionNode.eventName);
            final State sourceState = stateMap.get(transitionNode.sourceStateId);
            sourceState.addTransition(transition);
        }
        //---
        final State initialState = stateMap.get(this.initialStateId);
        stateMachine.setInitalState(initialState);
        return stateMachine;
    }

}

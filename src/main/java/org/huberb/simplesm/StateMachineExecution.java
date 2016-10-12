/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import org.huberb.simplesm.model.Transition;
import org.huberb.simplesm.model.State;
import org.huberb.simplesm.model.StateMachine;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 *
 * @author berni
 */
public class StateMachineExecution implements Serializable, IStateMachineExecution {

    private static final long serialVersionUID = 20160829L;
    private static final Logger logger = Logger.getLogger(StateMachineExecution.class.getName());
    private final StateMachine stateMachine;
    //---
    private State activeState;
    private IStep step = new LoggingNotificationStepImpl();

    public StateMachineExecution(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public IStep getStep() {
        return step;
    }

    protected void setStep(IStep step) {
        this.step = step;
    }

    //---
    @Override
    public void go() {
        final State nextActiveState = stateMachine.getInitalState();
        step.stepFromTo(null, null, nextActiveState);
        this.activeState = nextActiveState;
        logger.fine(() -> "go processing finished");
    }

    @Override
    public void trigger(String eventName) {
        logger.fine(() -> "trigger processing '" + eventName + "' started.");

        final StateTransitionMatching stm = new StateTransitionMatching();

        //---
        final Optional<Transition> optTransition;
        optTransition = stm.findTransition(activeState, eventName);

        //---
        final Consumer<Transition> consumerTransition = (Transition t) -> {
            final State nextActiveState = t.getTarget();
            step.stepFromTo(activeState, t, nextActiveState);
            this.activeState = nextActiveState;
        };
        final Consumer<Transition> loggingConsumer = (Transition t) -> {
            logger.info(() -> "trigger: " + eventName + " processing finished");
        };
        optTransition.ifPresent(t -> {
            consumerTransition.
                    andThen(loggingConsumer).
                    accept(t);
        });
    }

    public String getActiveStateId() {
        return activeState.getId();
    }

    Set<State> findMatchingStates(String stateId) {
        return stateMachine.findAnyMatchingStates(stateId);
    }

}

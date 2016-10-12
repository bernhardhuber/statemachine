/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import org.huberb.simplesm.model.Transition;
import org.huberb.simplesm.model.State;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 * @author berni
 */
public class StateTransitionMatching {

    final Predicate<State> finalStatePredicate = State::isFinalState;

    final BiPredicate<String, Transition> transitionEventNamePredicate = (String s, Transition t) -> {
        boolean result = t.getEventName().equals(s);
        return result;
    };

    Optional<Transition> findTransition(State s, String eventName) {
        Optional<Transition> optFoundTransition = Optional.empty();

        if (!finalStatePredicate.test(s)) {
            optFoundTransition = s.getTransitions().
                    stream().
                    filter((Transition t) -> this.transitionEventNamePredicate.test(eventName, t)).
                    findFirst();
        }
        return optFoundTransition;
    }
}

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
@FunctionalInterface
public interface IStep {

    void stepFromTo(State stateFrom, Transition transistion, State stateTo);

}

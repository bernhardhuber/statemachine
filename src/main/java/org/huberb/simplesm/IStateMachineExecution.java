/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

/**
 *
 * @author berni
 */
public interface IStateMachineExecution {

    void go();

    void trigger(String eventName);

}

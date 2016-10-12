/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import org.huberb.simplesm.model.StateMachineBuilder;
import org.huberb.simplesm.model.StateMachine;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author berni
 */
public class StateMachineExecutionSimpleTrafficLightTest {

    StateMachine createStateMachineAmpel() {
        StateMachineBuilder builder = new StateMachineBuilder();
        builder.initalstate("idle");
        builder.state("idle").transition("on", "red");
        builder.state("red").transition("switch", "green").transition("off", "idle");
        builder.state("green").transition("switch", "yellow");
        builder.state("yellow").transition("switch", "red");

        StateMachine stateMachine = builder.build();
        return stateMachine;
    }

    /**
     * Test of trigger method, of class StateMachineExecution.
     */
    @Test
    public void testTrigger() {
        final StateMachine stateMachine = createStateMachineAmpel();
        final StringBuilder sb = new StringBuilder();
        StateMachineExecution instance = new StateMachineExecution(stateMachine);
        instance.setStep(new LoggingNotificationStepImpl() {
            @Override
            protected void notify(NotificationEvent notificationEvent) {
                sb.append(notificationEvent).append(";");
            }
        });
        instance.go();
        assertEquals("idle", instance.getActiveStateId());
        instance.trigger("on");
        assertEquals("red", instance.getActiveStateId());
        instance.trigger("switch");
        assertEquals("green", instance.getActiveStateId());
        instance.trigger("switch");
        assertEquals("yellow", instance.getActiveStateId());
        instance.trigger("switch");
        assertEquals("red", instance.getActiveStateId());
        instance.trigger("off");
        assertEquals("idle", instance.getActiveStateId());
        assertEquals(
                "notificationType onentry,stateId idle;"
                + ""
                + "notificationType onexit,stateId idle;"
                + "notificationType transition,stateId on;"
                + "notificationType onentry,stateId red;"
                + ""
                + "notificationType onexit,stateId red;"
                + "notificationType transition,stateId switch;"
                + "notificationType onentry,stateId green;"
                + ""
                + "notificationType onexit,stateId green;"
                + "notificationType transition,stateId switch;"
                + "notificationType onentry,stateId yellow;"
                + ""
                + "notificationType onexit,stateId yellow;"
                + "notificationType transition,stateId switch;"
                + "notificationType onentry,stateId red;"
                + ""
                + "notificationType onexit,stateId red;"
                + "notificationType transition,stateId off;"
                + "notificationType onentry,stateId idle;", sb.toString());
    }

}

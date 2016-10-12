/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import org.huberb.simplesm.model.StateMachineBuilder;
import org.huberb.simplesm.model.StateMachine;
import org.huberb.simplesm.model.State;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author berni
 */
public class StateMachineExecutionFinalTest {

    public StateMachineExecutionFinalTest() {
    }

    StateMachine createStateMachineS1S2FinalS3() {
        StateMachineBuilder builder = new StateMachineBuilder();
        builder.initalstate("s1");
        builder.state("s1").transition("go-s2", "s2");
        builder.state("s2").transition("go-s3", "s3");
        builder.finalState("s3");
        StateMachine stateMachine = builder.build();
        return stateMachine;
    }

    /**
     * Test of trigger method, of class StateMachineExecution.
     */
    @Test
    public void testTrigger() {
        final StateMachine stateMachine = createStateMachineS1S2FinalS3();
        final StringBuilder sb = new StringBuilder();
        StateMachineExecution instance = new StateMachineExecution(stateMachine);
        instance.setStep(new LoggingNotificationStepImpl() {
            @Override
            protected void notify(NotificationEvent notificationEvent) {
                sb.append(notificationEvent).append(":/:");
            }
        });
        instance.go();
        instance.trigger("go-s2");
        instance.trigger("go-s3");
        assertEquals("s3", instance.getActiveStateId());
        final Set<State> s3States = instance.findMatchingStates("s3");
        assertEquals(1, s3States.size());
        assertEquals("s3", s3States.stream().findFirst().get().getId());
        assertEquals(true, s3States.stream().findFirst().get().isFinalState());
        assertEquals(
                "notificationType onentry,stateId s1:/:"
                + ""
                + "notificationType onexit,stateId s1:/:"
                + "notificationType transition,stateId go-s2:/:"
                + "notificationType onentry,stateId s2:/:"
                + ""
                + "notificationType onexit,stateId s2:/:"
                + "notificationType transition,stateId go-s3:/:"
                + "notificationType onentry,stateId s3:/:",
                sb.toString());
    }

}

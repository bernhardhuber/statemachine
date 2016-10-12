/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import org.huberb.simplesm.model.StateMachineBuilder;
import org.huberb.simplesm.model.StateMachine;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author berni
 */
public class StateMachineExecutionSerializingTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    StateMachine createStateMachineS1S2S3() {
        StateMachineBuilder builder = new StateMachineBuilder();
        builder.initalstate("s1");
        builder.state("s1").transition("go-s2", "s2");
        builder.state("s2").transition("go-s3", "s3");
        builder.state("s3");
        StateMachine stateMachine = builder.build();
        return stateMachine;
    }

    /**
     * Test of trigger method, of class StateMachineExecution.
     */
    @Test
    public void testTrigger() {
        final StateMachine stateMachine = createStateMachineS1S2S3();
        final StringBuilder sb = new StringBuilder();
        StateMachineExecution instance = new StateMachineExecution(stateMachine);
        instance.go();

        instance.trigger("go-s2");
        assertEquals("s2", instance.getActiveStateId());
        {
            final byte[] serializedBytes = SerializationUtils.serialize(instance);
            final StateMachineExecution instance2 = SerializationUtils.deserialize(serializedBytes);

            assertNotSame(instance, instance2);
            assertEquals(instance.getActiveStateId(), instance2.getActiveStateId());
            assertEquals("s2", instance2.getActiveStateId());
            instance2.trigger("go-s3");
            assertEquals("s3", instance2.getActiveStateId());
        }
        instance.trigger("go-s3");
        assertEquals("s3", instance.getActiveStateId());

    }

}

package com.labs.jharkhandi.gameoflife;

import junit.framework.TestCase;

import org.junit.Before;

/**
 * Created by sumit on 2/1/18.
 */
public class GameModelTest extends TestCase{

    private GameModel instance;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new GameModel(3,3);
    }

    public void test_init() throws Exception {
        assertEquals(3, instance.getRows());
        assertEquals(3, instance.getColumns());
    }

    public void test_is_alive() throws Exception {
        assertEquals(false, instance.isAlive(0,0));
    }

    public void test_make_alive() throws Exception {
        instance.makeAlive(0,0);
        assertTrue(instance.isAlive(0,0));

        instance.makeAlive(-1,0);
        assertFalse(instance.isAlive(-1,0));
    }

    public void test_make_dead() throws Exception {
        instance.makeAlive(0,0);
        instance.makeDead(0,0);
        assertFalse(instance.isAlive(0,0));
    }

    public void test_live_cell() throws Exception {
        instance.makeAlive(1,1);

        assertFalse(instance.willLive(1,1));

        instance.makeAlive(0,0);
        assertFalse(instance.willLive(1,1));

        instance.makeAlive(0,1);
        assertTrue(instance.willLive(1,1));

        instance.makeAlive(0,2);
        assertTrue(instance.willLive(1,1));

        instance.makeAlive(2,2);
        assertFalse(instance.willLive(1,1));

        instance.next();
        assertFalse(instance.willLive(1,1));
    }

    public void test_dead_cell() throws Exception {
        assertFalse(instance.willLive(1,1));

        instance.makeAlive(0,0);
        assertFalse(instance.willLive(1,1));

        instance.makeAlive(0,1);
        assertFalse(instance.willLive(1,1));

        instance.makeAlive(0,2);
        assertTrue(instance.willLive(1,1));

        instance.makeAlive(2,2);
        assertFalse(instance.willLive(1,1));

        instance.next();
        assertFalse(instance.willLive(1,1));
    }
}
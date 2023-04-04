package flowerforce.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flowerforce.model.utilities.Timer;
import flowerforce.model.utilities.TimerImpl;

final class TestTimer {

    private static final int NUMBER_OF_CYCLES = 60; 

    private Timer timer;

    /**
     * Sets up testing.
     */
    @BeforeEach
    void setUp() {
        this.timer = new TimerImpl(NUMBER_OF_CYCLES);
    }

    /**
     * Test Timer's isReady method.
     */
    @Test
    void testReady() {
        for (int i = 0; i < NUMBER_OF_CYCLES - 1; i++) {
            this.timer.updateState();
            assertFalse(this.timer.isReady());
        }
        this.timer.updateState();
        assertTrue(this.timer.isReady());
        this.timer.updateState();
        assertFalse(this.timer.isReady());
    }

    /**
     * Tests Timer's reset.
     */
    @Test
    void testReset() {
        for (int i = 0; i < 10; i++) {
            this.timer.updateState();
            assertFalse(this.timer.isReady());
        }
        this.timer.reset();
        for (int i = 0; i < NUMBER_OF_CYCLES - 1; i++) {
            this.timer.updateState();
            assertFalse(this.timer.isReady());
        }
        this.timer.updateState();
    }

    /**
     * Tests change in Timer's number of cycles.
     */
    @Test
    void testSetNumCycles() {
        for (int i = 0; i < NUMBER_OF_CYCLES - 1; i++) {
            this.timer.updateState();
            assertFalse(this.timer.isReady());
        }
        final int newNumCycles = NUMBER_OF_CYCLES - 10;
        this.timer.setNumCycles(newNumCycles);
        this.timer.updateState();
        assertTrue(this.timer.isReady());

        this.timer.reset();
        for (int i = 0; i < NUMBER_OF_CYCLES / 2; i++) {
            this.timer.updateState();
            assertFalse(this.timer.isReady());
        }
        this.timer.setNumCycles(NUMBER_OF_CYCLES);
        for (int i = 0; i < NUMBER_OF_CYCLES / 2 - 1; i++) {
            this.timer.updateState();
            assertFalse(this.timer.isReady());
        }
        this.timer.updateState();
        assertTrue(this.timer.isReady());
    }
}

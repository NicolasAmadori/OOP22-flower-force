package flowerforce.model.utilities;

/**
 * Models a timer based on game loop's cycles, used by entities for synchronization.
 */
public interface Timer {

    /**
     * Updates timer's internal state.
     */
    void updateState();

    /**
     * 
     * @return true if it's time to perform an action
     */
    boolean isReady();

    /**
     * Resets the timer to the initial value.
     */
    void reset();

    /**
     * 
     * @param numCycles before performing an action
     */
    void setNumCycles(int numCycles);
}

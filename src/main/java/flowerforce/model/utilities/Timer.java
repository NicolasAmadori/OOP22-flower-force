package flowerforce.model.utilities;

/**
 * Models a timer based on game loop's cycles, used by entities for synchronization.
 * The timer gets updated every cycle and becomes ready every N times it's updated
 * (with N greater than 0).
 */
public interface Timer {

    /**
     * Updates timer's internal state.
     */
    void updateState();

    /**
     * 
     * @return true if timer is ready
     */
    boolean isReady();

    /**
     * Resets the timer to the initial value.
     */
    void reset();

    /**
     * Sets the timer to a number of cycles (greater than 0).
     * @param numCycles number the timer is set to
     */
    void setNumCycles(int numCycles);
}

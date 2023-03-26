package flowerforce.common;

/**
 * Represents a generic Timer implementation.
 */
public class TimerImpl implements Timer {

    private int nCycles;
    private int timerCyclesCount;

    /**
     * 
     * @param nCycles indicates every how many game loop cycles an action must be performed
     */
    public TimerImpl(final int nCycles) {
        this.nCycles = nCycles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState() {
        this.timerCyclesCount = (this.timerCyclesCount + 1) % this.nCycles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReady() {
        return this.timerCyclesCount == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.timerCyclesCount = 0;
    }

    @Override
    public void setNumCycles(final int numCycles) {
        if (this.timerCyclesCount >= numCycles) {
            this.timerCyclesCount = numCycles - 1;
        }
        this.nCycles = numCycles;
    }


}

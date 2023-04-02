package flowerforce.model.utilities;

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
        verifyNCycles(nCycles);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNumCycles(final int newNCycles) {
        verifyNCycles(newNCycles);
        if (this.timerCyclesCount >= newNCycles) {
            this.timerCyclesCount = newNCycles - 1;
        }
        this.nCycles = newNCycles;
    }

    private static final void verifyNCycles(final int nCycles) {
        if (nCycles <= 0) {
            throw new IllegalArgumentException("Timer must be set to a positive integer!");
        }
    }


}

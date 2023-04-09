package flowerforce.model.entities;

import flowerforce.model.utilities.RenderingInformation;

/**
 * This utility class contains the possible plant's recharge times in the game.
 */
public final class RechargeTimes {
    private static final double FAST_RECHARGE_SECS = 7.5;
    private static final double SLOW_RECHARGE_SECS = 30;
    private static final double VERY_SLOW_RECHARGE_SECS = 50;
    private static final int FAST_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(FAST_RECHARGE_SECS);
    private static final int SLOW_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(SLOW_RECHARGE_SECS);
    private static final int VERY_SLOW_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(VERY_SLOW_RECHARGE_SECS);

    private RechargeTimes() {
    }

    /**
     * 
     * @return a fast plant's recharge time
     */
    static int getFastRechargeTime() {
        return FAST_RECHARGE_TICKS;
    }

    /**
     * 
     * @return a slow plant's recharge time
     */
    static int getSlowRechargeTime() {
        return SLOW_RECHARGE_TICKS;
    }

    /**
     * 
     * @return a very slow plant's recharge time
     */
    static int getVerySlowRechargeTime() {
        return VERY_SLOW_RECHARGE_TICKS;
    }
}

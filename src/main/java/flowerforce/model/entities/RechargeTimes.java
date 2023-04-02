package flowerforce.model.entities;

import flowerforce.model.utilities.RenderingInformation;

public final class RechargeTimes {
    private static final double FAST_RECHARGE_SECS = 7.5;
    private static final double SLOW_RECHARGE_SECS = 30;
    private static final double VERY_SLOW_RECHARGE_SECS = 50;
    private static final int FAST_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(FAST_RECHARGE_SECS);
    private static final int SLOW_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(SLOW_RECHARGE_SECS);
    private static final int VERY_SLOW_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(VERY_SLOW_RECHARGE_SECS);

    final static int getFastRechargeTime() {
        return FAST_RECHARGE_TICKS;
    }

    final static int getSlowRechargeTime() {
        return SLOW_RECHARGE_TICKS;
    }

    final static int getVerySlowRechargeTime() {
        return VERY_SLOW_RECHARGE_TICKS;
    }
}

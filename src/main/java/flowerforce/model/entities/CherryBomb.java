package flowerforce.model.entities;

import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

/**
 * Models a CherryBomb, a plant that explodes.
 */
public class CherryBomb extends BaseExplodingPlant {

    private static final int CHERRY_HEALTH = 3000;
    private static final int CHERRY_COST = 150;
    private static final int DAMAGE = 1800;
    private static final int CHERRY_RADIUS = 2;
    private static final double SECONDS_BEFORE_EXPLOSION = 1.2;
    private static final String CHERRY_NAME = "cherrybomb";

    /**
     * 
     * @param pos the initial position to place the entity in
     */
    public CherryBomb(final Point2D pos) {
        super(
            pos,
            new TimerImpl(RenderingInformation.convertSecondsToCycles(SECONDS_BEFORE_EXPLOSION)), 
            CHERRY_HEALTH,
            CHERRY_COST,
            RechargeTimes.getVerySlowRechargeTime(),
            DAMAGE,
            CHERRY_RADIUS,
            CHERRY_NAME
        );
    }

}

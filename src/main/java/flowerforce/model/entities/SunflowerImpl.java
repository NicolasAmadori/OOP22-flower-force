package flowerforce.model.entities;

import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * Models a flower that doesn't attack zombies but produces suns.
 */
public class SunflowerImpl extends AbstractPlant implements Sunflower {


        /**
     * 
     * @param pos the initial position to place the sunflower in
     * @param sunProductionTimer used to produce suns at the right time
     * @param health the starting health of the sunflower
     * @param cost sunflower's cost
     * @param rechargeTime sunflower's recharge time
     * @param plantName sunflower's name
     */
    public SunflowerImpl(
        final Point2D pos,
        final Timer sunProductionTimer,
        final int health,
        final int cost,
        final int rechargeTime,
        final String plantName
    ) {
        super(pos, sunProductionTimer, health, cost, rechargeTime, plantName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSunGenerated() {
        return this.getTimer().isReady();
    }

}

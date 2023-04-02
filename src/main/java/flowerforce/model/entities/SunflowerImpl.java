package flowerforce.model.entities;

import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * Models a flower that doesn't attack zombies but produces suns.
 */
public class SunflowerImpl extends AbstractPlant implements Sunflower {


    /**
     * 
     * @param pos the initial position to place the entity in
     * @param plantType the type of the plant
     */
    public SunflowerImpl(final Point2D pos, final Timer sunProductionTimer, final int health, final int cost, final int rechargeTime, final String plantName) {
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

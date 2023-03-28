package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.utilities.Timer;
import flowerforce.model.utilities.TimerImpl;
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
    public SunflowerImpl(final Point2D pos, final Timer sunProductionTimer, final double health, final Plants plantType) {
        super(pos, sunProductionTimer, health, plantType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSunGenerated() {
        return this.getTimer().isReady();
    }

}

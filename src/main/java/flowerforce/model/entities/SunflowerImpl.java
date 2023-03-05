package flowerforce.model.entities;

import flowerforce.common.TimerImpl;
import flowerforce.model.entities.IdConverter.Plants;
import javafx.geometry.Point2D;

/**
 * Models a flower that doesn't attack zombies but produces suns.
 */
public class SunflowerImpl extends AbstractPlant implements Sunflower {


    private static final int STANDARD_SUNPRODUCING_TIME = 60;
    private static final double STANDARD_SUNFLOWER_HEALTH = 100.0;

    /**
     * 
     * @param pos the initial position to place the entity in
     */
    public SunflowerImpl(final Point2D pos, final Plants plantType) {
        super(pos, new TimerImpl(STANDARD_SUNPRODUCING_TIME), STANDARD_SUNFLOWER_HEALTH, plantType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSunGenerated() {
        return this.getTimer().isReady();
    }

}

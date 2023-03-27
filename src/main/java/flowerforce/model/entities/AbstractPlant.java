package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * Models a generic plant.
 */
public abstract class AbstractPlant extends AbstractLivingEntity implements Plant {

    private final Plants plantType;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     * @param health the starting health of the entity
     * @param plantType the type of the plant
     */
    public AbstractPlant(final Point2D pos, final Timer timer, final double health, final Plants plantType) {
        super(pos, timer, health);
        this.plantType = plantType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plants getPlantType() {
        return this.plantType;
    }

}

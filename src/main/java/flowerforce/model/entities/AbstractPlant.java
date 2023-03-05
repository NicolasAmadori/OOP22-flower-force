package flowerforce.model.entities;

import flowerforce.common.Timer;
import flowerforce.model.entities.IdConverter.Plants;
import javafx.geometry.Point2D;

public abstract class AbstractPlant extends AbstractLivingEntity implements Plant {

    private final Plants plantType;

    public AbstractPlant(final Point2D pos, final Timer timer, final double health, Plants plantType) {
        super(pos, timer, health);
        this.plantType = plantType;
    }

    @Override
    public Plants getPlantType() {
        return this.plantType;
    }
    
}

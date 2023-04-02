package flowerforce.model.entities;

import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * Models a generic plant.
 */
public abstract class AbstractPlant extends AbstractLivingEntity implements Plant {

    private final int cost;
    private final int rechargeTime;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     * @param health the starting health of the entity
     * @param plantType the type of the plant
     */
    public AbstractPlant(final Point2D pos, final Timer timer, final int health, final int cost, final int rechargeTime, final String plantName) {
        super(pos, timer, health, plantName);
        this.cost = cost;
        this.rechargeTime = rechargeTime;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public int getRechargeTime() {
        return this.rechargeTime;
    }

}

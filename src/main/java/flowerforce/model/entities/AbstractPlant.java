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
     * @param cost plant's cost
     * @param rechargeTime plant's recharge time
     * @param plantName plant's name
     */
    public AbstractPlant(
        final Point2D pos,
        final Timer timer,
        final int health,
        final int cost,
        final int rechargeTime,
        final String plantName
    ) {
        super(pos, timer, health, plantName);
        this.cost = cost;
        this.rechargeTime = rechargeTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCost() {
        return this.cost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRechargeTime() {
        return this.rechargeTime;
    }

}

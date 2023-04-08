package flowerforce.model.entities;

import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * Represents an abstract {@link LivingEntity}.
 */
public abstract class AbstractLivingEntity extends AbstractEntity implements LivingEntity {

    private int health;
    private final Timer timer;


    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     * @param health the starting health of the entity
     * @param entityName the name of the entity
     */
    protected AbstractLivingEntity(final Point2D pos, final Timer timer, final int health, final String entityName) {
        super(pos, entityName);
        this.timer = timer;
        this.health = health;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.health <= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void receiveDamage(final int damage) {
        this.health -= damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState() {
        this.timer.updateState();
    }

    /**
     * @return entity's timer.
     */
    protected Timer getTimer() {
        return this.timer;
    }

}

package flowerforce.model.entities.plants;

import java.util.Set;

import flowerforce.model.entities.zombies.Zombie;
import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * This is an implementation of {@link ExplodingPlant}.
 */
public class BaseExplodingPlant extends AbstractPlant implements ExplodingPlant {

    private final int damage;
    private final int radius;

    /**
     * 
     * @param pos the position to place the plant in
     * @param timer the plant's timer
     * @param health the plant's health
     * @param cost the plant's cost
     * @param rechargeTime the plant's recharge time
     * @param damage the plant's damage
     * @param radius the explosion radius
     * @param plantName the plant's name
     */
    protected BaseExplodingPlant(
        final Point2D pos,
        final Timer timer,
        final int health,
        final int cost,
        final int rechargeTime,
        final int damage,
        final int radius,
        final String plantName
    ) {
        super(pos, timer, health, cost, rechargeTime, plantName);
        this.damage = damage;
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void explodeOver(final Set<Zombie> zombieSet) {
        zombieSet.forEach(z -> z.receiveDamage(damage));
        this.receiveDamage(this.getHealth());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRadius() {
        return radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasExploded() {
        return this.getTimer().isReady();
    }

}

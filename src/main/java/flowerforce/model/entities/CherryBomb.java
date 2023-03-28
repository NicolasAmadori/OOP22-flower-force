package flowerforce.model.entities;

import java.util.List;

import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

/**
 * Models a CherryBomb, an exploding plant that explodes after a certain time
 * and damages enemies in its and adjacent cells.
 */
public class CherryBomb extends AbstractPlant implements ExplodingPlant {

    private static final double CHERRY_HEALTH = 1.0;
    private static final double DAMAGE = 1800.0;
    private static final int CHERRY_RADIUS = 1;
    private static final double SECONDS_BEFORE_EXPLOSION = 1.2;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param plantType the type of the plant
     */
    public CherryBomb(final Point2D pos, final Plants plantType) {
        super(
            pos,
            new TimerImpl(RenderingInformation.convertSecondsToCycles(SECONDS_BEFORE_EXPLOSION)), 
            CHERRY_HEALTH,
            plantType
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void explodeOver(final List<Zombie> zombieList) {
        zombieList.stream().peek(z -> z.receiveDamage(DAMAGE));
        this.receiveDamage(this.getHealth());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRadius() {
        return CHERRY_RADIUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasExploded() {
        return this.getTimer().isReady();
    }

}

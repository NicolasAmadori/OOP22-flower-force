package flowerforce.model.entities;

import java.util.List;

import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

/**
 * Models a CherryBomb, an exploding plant that explodes after a certain time
 * and damages enemies in its and adjacent cells.
 */
public class CherryBomb extends AbstractPlant implements ExplodingPlant {

    private static final int CHERRY_HEALTH = 1;
    private static final int CHERRY_COST = 150;
    private static final int DAMAGE = 1800;
    private static final int CHERRY_RADIUS = 1;
    private static final double SECONDS_BEFORE_EXPLOSION = 1.2;
    private static final String CHERRY_NAME = "cherrybomb";

    /**
     * 
     * @param pos the initial position to place the entity in
     */
    public CherryBomb(final Point2D pos) {
        super(
            pos,
            new TimerImpl(RenderingInformation.convertSecondsToCycles(SECONDS_BEFORE_EXPLOSION)), 
            CHERRY_HEALTH,
            CHERRY_COST,
            RechargeTimes.getVerySlowRechargeTime(),
            CHERRY_NAME
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void explodeOver(final List<Zombie> zombieList) {
        zombieList.forEach(z -> z.receiveDamage(DAMAGE));
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

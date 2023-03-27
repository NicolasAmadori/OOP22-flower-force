package flowerforce.model.entities;

import java.util.Optional;
import java.util.function.Consumer;

import flowerforce.model.utilities.RenderingInformation;
import javafx.geometry.Point2D;

/**
 * Models a generic bullet.
 */
public class BulletImpl extends AbstractEntity implements Bullet {

    private static final double SECS_PER_CELL = 0.5;
    private static final int DELTA = RenderingInformation.getDeltaFromSecondsPerCell(SECS_PER_CELL);

    private final double damage;
    private boolean hasHit;
    private Optional<Consumer<Zombie>> actionOverZombie = Optional.empty();

    /**
     * 
     * @param pos the initial position to place the bullet in
     * @param damage the damage that the bullet does to zombies
     */
    public BulletImpl(final Point2D pos, final double damage) {
        super(pos);
        this.damage = damage;
    }


    public BulletImpl(final Point2D pos, final double damage, final Consumer<Zombie> action) {
        this(pos, damage);
        this.actionOverZombie = Optional.of(action);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        this.setPosition(new Point2D(this.getPosition().getX() + DELTA, this.getPosition().getY()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.hasHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hit(final Zombie zombie) {
        this.hasHit = true;
        zombie.receiveDamage(this.damage);
        this.actionOverZombie.ifPresent(x -> x.accept(zombie));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDeltaMovement() {
        return DELTA;
    }

}

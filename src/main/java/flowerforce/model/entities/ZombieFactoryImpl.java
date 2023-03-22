package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Zombies;
import javafx.geometry.Point2D;

/**
 * Implementation of {@link ZombieFactory}.
 */
public class ZombieFactoryImpl implements ZombieFactory {

    private static final double BASIC_HEALTH = 1.0;
    private static final double BASIC_DAMAGE = 15;
    private static final int BASIC_DELTA = 5;
    private static final double CONE_HEALTH = 2.0;
    private static final double BUCKET_HEALTH = 9.0;
    private static final double HELMET_HEALTH = 12.0;
    private static final int RUNNING_DELTA = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombie basic(final Point2D position, final Zombies zombieType) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position, zombieType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombie conehead(final Point2D position, final Zombies zombieType) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH + CONE_HEALTH, position, zombieType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombie buckethead(final Point2D position, final Zombies zombieType) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH + BUCKET_HEALTH, position, zombieType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombie running(final Point2D position, final Zombies zombieType) {
        return new ZombieImpl(RUNNING_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position, zombieType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombie quarterback(final Point2D position, final Zombies zombieType) {
        return new ZombieImpl(RUNNING_DELTA, BASIC_DAMAGE, BASIC_HEALTH + HELMET_HEALTH, position, zombieType);
    }

}

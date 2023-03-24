package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Zombies;
import javafx.geometry.Point2D;

/**
 * Implementation of {@link ZombieFactory}.
 */
public class ZombieFactoryImpl implements ZombieFactory {

    private static final int BASIC_HEALTH = 181;
    private static final int BASIC_DAMAGE = 10;
    private static final int BASIC_DELTA = 2;
    private static final int CONE_HEALTH = 370;
    private static final int BUCKET_HEALTH = 1100;
    private static final int HELMET_HEALTH = 1400;
    private static final int RUNNING_DELTA = 2 * BASIC_DELTA;

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

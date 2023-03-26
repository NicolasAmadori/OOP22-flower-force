package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Zombies;
import flowerforce.model.game.Yard;
import flowerforce.model.utilities.RenderingInformation;
import javafx.geometry.Point2D;

/**
 * Implementation of {@link ZombieFactory}.
 */
public class ZombieFactoryImpl implements ZombieFactory {

    private static final int BASIC_HEALTH = 181;
    private static final int BASIC_DAMAGE = 100;
    private static final int CONE_HEALTH = 370;
    private static final int BUCKET_HEALTH = 1100;
    private static final int HELMET_HEALTH = 1400;
    private static final int NEWSPAPER_HEALTH = 150;
    private static final int GARGANTUAR_HEALTH = 3000;
    private static final int GARGANTUAR_DAMAGE = 10_000;
    private static final double BASIC_SECS_PER_CELL = 4.7;
    private static final int BASIC_TICKS_PER_CELL = (int) (BASIC_SECS_PER_CELL * RenderingInformation.getFramesPerSecond());
    private static final int BASIC_DELTA = (int) (Yard.getCellDimension().getWidth() / BASIC_TICKS_PER_CELL);
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

    @Override
    public Zombie newspaper(Point2D position, Zombies zombieType) {
        return new NewspaperZombie(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position, zombieType, NEWSPAPER_HEALTH);
    }

    @Override
    public Zombie gargantuar(Point2D position, Zombies zombieType) {
        return new ZombieImpl(BASIC_DELTA, GARGANTUAR_DAMAGE, GARGANTUAR_HEALTH, position, zombieType);
    }

}

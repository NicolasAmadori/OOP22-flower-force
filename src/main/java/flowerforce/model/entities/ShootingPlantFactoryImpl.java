package flowerforce.model.entities;

import flowerforce.common.TimerImpl;
import flowerforce.model.entities.IdConverter.Plants;
import javafx.geometry.Point2D;

/**
 * An implementation of ShootingPlantFactory.
 */
public class ShootingPlantFactoryImpl implements ShootingPlantFactory {

    private static final int STANDARD_SHOOTING_TIME = 60;
    private static final double STANDARD_SHOOTER_HEALTH = 100.0;

    /**
     * {@inheritDoc}
     */
    @Override
    public ShootingPlant common(final Point2D pos, final Plants plantType) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            StandardBullet.class,
            plantType
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShootingPlant snow(final Point2D pos, final Plants plantType) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            SnowBullet.class,
            plantType
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShootingPlant fire(final Point2D pos, final Plants plantType) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            FireBullet.class,
            plantType
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShootingPlant fast(final Point2D pos, final Plants plantType) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME / 2),
            STANDARD_SHOOTER_HEALTH,
            StandardBullet.class,
            plantType
        );
    }

}

package flowerforce.model.entities;

import flowerforce.common.TimerImpl;
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
    public ShootingPlant createCommonShooterPlant(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            StandardBullet.class
        );
    }

    /**
     * {@inheritDoc}
     */
    public ShootingPlant createSnowPlant(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            SnowBullet.class
        );
    }

    /**
     * {@inheritDoc}
     */
    public ShootingPlant createFirePlant(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            FireBullet.class
        );
    }

    /**
     * {@inheritDoc}
     */
    public ShootingPlant createFastShooterPlant(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME / 2),
            STANDARD_SHOOTER_HEALTH,
            StandardBullet.class
        );
    }

}

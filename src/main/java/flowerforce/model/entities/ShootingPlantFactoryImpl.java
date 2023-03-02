package flowerforce.model.entities;

import flowerforce.common.TimerImpl;
import javafx.geometry.Point2D;

/**
 * An implementation of ShootingPlantFactory.
 */
public class ShootingPlantFactoryImpl {

    private static final int STANDARD_SHOOTING_TIME = 60;
    private static final double STANDARD_SHOOTER_HEALTH = 100.0;

    /**
     * {@inheritDoc}
     */
    ShootingPlant createShooter(final Point2D pos) {
        return new ShootingPlantImpl(pos, new TimerImpl(STANDARD_SHOOTING_TIME), STANDARD_SHOOTER_HEALTH, StandardBullet.class);
    }

    /**
     * {@inheritDoc}
     */
    ShootingPlant createSnowPlant(final Point2D pos) {
        return new ShootingPlantImpl(pos, new TimerImpl(STANDARD_SHOOTING_TIME), STANDARD_SHOOTER_HEALTH, SnowBullet.class);
    }

    /**
     * {@inheritDoc}
     */
    ShootingPlant createFirePlant(final Point2D pos) {
        return new ShootingPlantImpl(pos, new TimerImpl(STANDARD_SHOOTING_TIME), STANDARD_SHOOTER_HEALTH, FireBullet.class);
    }

    /**
     * {@inheritDoc}
     */
    ShootingPlant createFastShooter(final Point2D pos) {
        return new ShootingPlantImpl(pos, new TimerImpl(STANDARD_SHOOTING_TIME / 2), STANDARD_SHOOTER_HEALTH, StandardBullet.class);
    }
}

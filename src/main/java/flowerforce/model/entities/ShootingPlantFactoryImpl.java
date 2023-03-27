package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

/**
 * An implementation of ShootingPlantFactory.
 */
public class ShootingPlantFactoryImpl implements ShootingPlantFactory {

    private static final double STANDARD_SECS_SHOOTING_TIME = 1.425;
    private static final int STANDARD_SHOOTING_TIME = (int) (
        STANDARD_SECS_SHOOTING_TIME * RenderingInformation.getFramesPerSecond()
    );
    private static final double STANDARD_SHOOTER_HEALTH = 300.0;

    private final BulletFactory bulletFactory = new BulletFactoryImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public ShootingPlant common(final Point2D pos, final Plants plantType) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            () -> this.bulletFactory.createStandardBullet(getBulletPos(pos)),
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
            () -> this.bulletFactory.createSnowdBullet(getBulletPos(pos)),
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
            () -> this.bulletFactory.createFireBullet(getBulletPos(pos)),
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
            () -> this.bulletFactory.createStandardBullet(getBulletPos(pos)),
            plantType
        );
    }

    private static Point2D getBulletPos(final Point2D plantPos) {
        return new Point2D(plantPos.getX() + 1, plantPos.getY());
    }

}

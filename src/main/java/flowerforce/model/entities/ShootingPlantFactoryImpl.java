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
    private static final int STANDARD_SHOOTER_HEALTH = 300;
    private static final int COMMON_SHOOTER_COST = 100;
    private static final int SNOW_SHOOTER_COST = 175;
    private static final int FIRE_SHOOTER_COST = 175;
    private static final int FAST_SHOOTER_COST = 225;


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
            COMMON_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
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
            SNOW_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
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
            FIRE_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
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
            FAST_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            plantType
        );
    }

    private static Point2D getBulletPos(final Point2D plantPos) {
        return new Point2D(plantPos.getX() + 1, plantPos.getY());
    }

}

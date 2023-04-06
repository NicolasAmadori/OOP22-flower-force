package flowerforce.model.entities;

import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

/**
 * A factory that generates {@link ShootingPlant} instances.
 */
public final class ShootingPlantFactory {

    private static final double STANDARD_SECS_SHOOTING_TIME = 1.425;
    private static final int STANDARD_SHOOTING_TIME = (int) (
        STANDARD_SECS_SHOOTING_TIME * RenderingInformation.getFramesPerSecond()
    );
    private static final int STANDARD_SHOOTER_HEALTH = 300;
    private static final int STRONG_SHOOTER_HEALTH = STANDARD_SHOOTER_HEALTH / 10;

    private static final int COMMON_SHOOTER_COST = 100;
    private static final int SNOW_SHOOTER_COST = 175;
    private static final int FIRE_SHOOTER_COST = 225;
    private static final int FAST_SHOOTER_COST = 200;
    private static final int STRONG_SHOOTER_COST = 250;

    private ShootingPlantFactory() {
    }

    /**
     * Creates a Pea Shooter Plant.
     * @param pos the position where to place it
     * @return a Pea Shooter Plant
     */
    public static ShootingPlant createPeaShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            () -> BulletFactory.createStandardBullet(new Point2D(pos.getX(), pos.getY())),
            COMMON_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            "peashooter"
        );
    }

    /**
     * Creates a Snow Shooter Plant.
     * @param pos the position where to place it
     * @return a Snow Shooter Plant
     */
    public static ShootingPlant createSnowShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            () -> BulletFactory.createSnowBullet(new Point2D(pos.getX(), pos.getY())),
            SNOW_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            "snowshooter"
        );
    }

    /**
     * Creates a Fire Shooter.
     * @param pos the position where to place it
     * @return a Fire Shooter Plant
     */
    public static ShootingPlant createFireShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            () -> BulletFactory.createFireBullet(new Point2D(pos.getX(), pos.getY())),
            FIRE_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            "fireshooter"
        );
    }

    /**
     * Creates a Fast Shooter.
     * @param pos the position where to place it
     * @return a Fast Shooter Plant
     */
    public static ShootingPlant createFastShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME / 2),
            STANDARD_SHOOTER_HEALTH,
            () -> BulletFactory.createStandardBullet(new Point2D(pos.getX(), pos.getY())),
            FAST_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            "fastshooter"
        );
    }

    /**
     * Creates a Strong Shooter.
     * @param pos the position where to place it
     * @return a Strong Shooter Plant
     */
    public static ShootingPlant createStrongShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME * 10),
            STRONG_SHOOTER_HEALTH,
            () -> BulletFactory.createStrongBullet(new Point2D(pos.getX(), pos.getY())),
            STRONG_SHOOTER_COST,
            RechargeTimes.getVerySlowRechargeTime(),
            "strongshooter"
        );
    }
}

package flowerforce.model.entities;

import flowerforce.model.game.Yard;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

/**
 * A factory that generates {@link ShootingPlant} instances.
 */
public class ShootingPlantFactory {

    private static final double STANDARD_SECS_SHOOTING_TIME = 1.425;
    private static final int STANDARD_SHOOTING_TIME = (int) (
        STANDARD_SECS_SHOOTING_TIME * RenderingInformation.getFramesPerSecond()
    );
    private static final int STANDARD_SHOOTER_HEALTH = 300;
    private static final int COMMON_SHOOTER_COST = 100;
    private static final int SNOW_SHOOTER_COST = 175;
    private static final int FIRE_SHOOTER_COST = 175;
    private static final int FAST_SHOOTER_COST = 225;

    private ShootingPlantFactory() {
    }

    /**
     * 
     * @param pos the position where to place it
     * @return a Common Shooter Plant
     */
    public static ShootingPlant createPeaShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            () -> BulletFactory.createStandardBullet(new Point2D(pos.getX() - Yard.getCellDimension().getWidth() / 2, pos.getY())),
            COMMON_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            "peashooter"
        );
    }

    /**
     * 
     * @param pos the position where to place it
     * @return a Snow Plant
     */
    public static ShootingPlant createSnowShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            () -> BulletFactory.createSnowdBullet(new Point2D(pos.getX() - Yard.getCellDimension().getWidth() / 2, pos.getY())),
            SNOW_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            "snowshooter"
        );
    }

    /**
     * 
     * @param pos the position where to place it
     * @return a Fire Plant
     */
    public static ShootingPlant createFireShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME),
            STANDARD_SHOOTER_HEALTH,
            () -> BulletFactory.createFireBullet(new Point2D(pos.getX() - Yard.getCellDimension().getWidth() / 2, pos.getY())),
            FIRE_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            "fireshooter"
        );
    }

    /**
     * 
     * @param pos the position where to place it
     * @return a Fast Shooter Plant
     */
    public static ShootingPlant createFastShooter(final Point2D pos) {
        return new ShootingPlantImpl(
            pos,
            new TimerImpl(STANDARD_SHOOTING_TIME / 2),
            STANDARD_SHOOTER_HEALTH,
            () -> BulletFactory.createStandardBullet(new Point2D(pos.getX() - Yard.getCellDimension().getWidth() / 2, pos.getY())),
            FAST_SHOOTER_COST,
            RechargeTimes.getFastRechargeTime(),
            "fastshooter"
        );
    }
}

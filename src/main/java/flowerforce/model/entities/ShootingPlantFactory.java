package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * A factory that generates {@link ShootingPlant} instances.
 */
public interface ShootingPlantFactory {

    /**
     * 
     * @param pos the position where to place it
     * @return a Common Shooter Plant
     */
    ShootingPlant createCommonShooterPlant(Point2D pos);

    /**
     * 
     * @param pos the position where to place it
     * @return a Snow Plant
     */
    ShootingPlant createSnowPlant(Point2D pos);

    /**
     * 
     * @param pos the position where to place it
     * @return a Fire Plant
     */
    ShootingPlant createFirePlant(Point2D pos);

    /**
     * 
     * @param pos the position where to place it
     * @return a Fast Shooter Plant
     */
    ShootingPlant createFastShooterPlant(Point2D pos);
}

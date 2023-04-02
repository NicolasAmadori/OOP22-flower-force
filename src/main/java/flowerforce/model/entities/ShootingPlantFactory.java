package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * A factory that generates {@link ShootingPlant} instances.
 */
public interface ShootingPlantFactory {

    /**
     * 
     * @param pos the position where to place it
     * @param plantType the type of the plant
     * @return a Common Shooter Plant
     */
    ShootingPlant peashooter(Point2D pos);

    /**
     * 
     * @param pos the position where to place it
     * @param plantType the type of the plant
     * @return a Snow Plant
     */
    ShootingPlant snow(Point2D pos);

    /**
     * 
     * @param pos the position where to place it
     * @param plantType the type of the plant
     * @return a Fire Plant
     */
    ShootingPlant fire(Point2D pos);

    /**
     * 
     * @param pos the position where to place it
     * @param plantType the type of the plant
     * @return a Fast Shooter Plant
     */
    ShootingPlant fast(Point2D pos);
}

package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models a factory for Sunflower entities.
 */
public interface SunflowerFactory {

    /**
     * Creates a common sunflower.
     * @param pos the position to place the entity in
     * @return a common sunflower with the given position
     */
    Sunflower createCommonSunflower(Point2D pos);

    /**
     * Creates a double sunflower.
     * @param pos the position to place the entity in
     * @return a double sunflower with the given position
     */
    Sunflower createDoubleSunflower(Point2D pos);

}

package flowerforce.model.game;

import flowerforce.model.entities.plants.Plant;
import flowerforce.model.entities.plants.PlantInfo;
import javafx.geometry.Point2D;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * This is a shop class to show and buy new plants.
 */
public interface Shop {
    /**
     * Get all the plants contained in the shop.
     * @return A map where the key contain the information of the plant,
     * and the value is a boolean, true if the plant is purchasable, false otherwise
     */
    Map<PlantInfo, Boolean> getPlants();

    /**
     * Buy a certain plant from the shop.
     * @param plantInfo the information of the plant to buy
     * @return true if the plant was successfully bought, false otherwise
     */
    boolean buyPlant(PlantInfo plantInfo);

    /**
     * Get all the function producer of the plants instances.
     * @return A set of function
     */
    Set<Function<Point2D, Plant>> getBoughtPlantsFunctions();
}

package flowerforce.model.game;

import flowerforce.model.entities.Plant;
import javafx.geometry.Point2D;
import javafx.util.Pair;
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
     * nd the value is a boolean, true if the plant is already bought, false otherwise
     */
    Map<Pair<String, Integer>, Boolean> getPurchasablePlants();

    /**
     * Buy a certain plant from the shop.
     * @param plantInfo the information of the plant to buy
     * @return true if the plant was successfully bought, false otherwise
     */
    boolean buyPlant(Pair<String, Integer> plantInfo);

    /**
     * Get all the function producer of the plants instances.
     * @return A set of function
     */
    Set<Function<Point2D, Plant>> getBoughtPlantsProducer();
}

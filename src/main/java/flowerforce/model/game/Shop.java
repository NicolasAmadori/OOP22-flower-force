package flowerforce.model.game;

import javafx.util.Pair;
import java.util.Map;

/**
 * This is a shop class to show and buy new plants.
 */
public interface Shop {
    /**
     * Get all the plants contained in the shop.
     * @return A map where the key contain the information of the plant,
     * nd the value is a boolean, true if the plant is already bought, false otherwise
     */
    Map<Pair<String, Integer>, Boolean>  getPlants();

    /**
     * Buy a certain plant from the shop.
     * @param plantInfo the information of the plant to buy
     * @return true if the plant was successfully bought, false otherwise
     */
    boolean buyPlant(Pair<String, Integer> plantInfo);
}

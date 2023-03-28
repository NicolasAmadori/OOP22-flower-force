package flowerforce.model.game;
import flowerforce.model.entities.IdConverter;
import javafx.util.Pair;
import java.util.Map;

/**
 * This is a shop class to show and buy new plants.
 */
public interface Shop {
    /**
     * This method return a map with plants number as keys, and a pair with cost e isUnlocked as values.
     * @return The map representing all the plants available to buy.
     */
    Map<Integer, Pair<Integer, Boolean>> getPlants();

    /**
     * Buy a plant, if possible.
     * @param id the id of the plant to buy
     * @return true if the operation was successfull, false otherwise
     */
    boolean buyPlant(int id);
}

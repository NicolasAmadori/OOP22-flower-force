package flowerforce.model.game;
import javafx.util.Pair;

import java.util.Map;
import java.util.Set;

public interface Shop {

    Map<BuyablePlant, Boolean> getPlants();
    boolean buyPlant(int id);
}

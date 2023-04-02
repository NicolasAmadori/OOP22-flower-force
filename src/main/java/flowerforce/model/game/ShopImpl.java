package flowerforce.model.game;

import flowerforce.model.entities.Plant;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This is an implementation of {@link Shop}
 */
public class ShopImpl implements Shop{
    private Player player;
    private Map<Pair<String, Integer>, Supplier<Plant>> buyablePlants = new LinkedHashMap<>();

    private Map<Pair<String, Integer>, Supplier<Plant>> boughtPlants = new LinkedHashMap<>();

    /**
     * This is a constructor for a new shop instance.
     * @param player The player to add bought plants to
     */
    public ShopImpl(Player player, Set<Pair<Supplier<Plant>, Integer>> buyablePlants) {
        this.player = player;

        //Adding all plants in the map
        buyablePlants.forEach(p -> {
            this.buyablePlants.put(new Pair<>(p.getKey().get().getName(), p.getValue()),
                                   p.getKey());
        });

        //add all the already bought plants to the boughPlants Map
        int i = 0;
        Set<Pair<String, Integer>> plantToRemove = new HashSet<>();
        for (var p : this.buyablePlants.entrySet()) {
            if(this.player.getPlantsIds().contains(i)) {
                this.boughtPlants.put(p.getKey(), p.getValue());
                plantToRemove.add(p.getKey());
            }
            i++;
        }

        //Remove bought plants from the buyable Plants Map
        plantToRemove.forEach(p -> this.buyablePlants.remove(p));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Pair<String, Integer>, Boolean> getPlants() {
        Map<Pair<String, Integer>, Boolean> outputMap = new HashMap<>();
        this.buyablePlants.keySet().forEach(k -> outputMap.put(k, false));
        this.boughtPlants.keySet().forEach(k -> outputMap.put(k, true));
        return outputMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyPlant(final Pair<String, Integer> plantInfo) {
        //TODO: refactor
        if(this.buyablePlants.containsKey(plantInfo) &&
                this.player.subtractCoins(plantInfo.getValue())) {
            this.player.addPlant(getKeyIndex(plantInfo));
            this.buyablePlants.remove(plantInfo);
            this.boughtPlants.put(plantInfo, this.buyablePlants.get(plantInfo));
            return true;
        }
        return false;
    }

    private int getKeyIndex(final Pair<String, Integer> plantInfo) {
        int i = 0;
        for (var e: this.buyablePlants.entrySet()) {
            if (e.equals(plantInfo)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}

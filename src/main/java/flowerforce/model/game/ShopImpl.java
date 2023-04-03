package flowerforce.model.game;

import flowerforce.model.entities.CherryBomb;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.SunflowerFactoryImpl;
import javafx.util.Pair;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;

/**
 * This is an implementation of {@link Shop}
 */
public class ShopImpl implements Shop{
    private static final Set<Pair<Function<Point2D, Plant>, Integer>> SHOP_PLANTS = Set.of(
            new Pair<> ((pos) -> new SunflowerFactoryImpl().createDoubleSunflower(pos), 10),
            new Pair<> (CherryBomb::new, 20)
    ); //TODO: set actual costs
    private final Player player;
    private final Map<Pair<String, Integer>, Function<Point2D, Plant>> purchasablePlants = new LinkedHashMap<>();

    private final Map<Pair<String, Integer>, Function<Point2D, Plant>> boughtPlants = new LinkedHashMap<>();

    /**
     * This is a constructor for a new shop instance.
     * @param player The player to add bought plants to
     */
    public ShopImpl(Player player) {
        this.player = player;

        //Adding all plants in the map
        final Point2D samplePoint = new Point2D(0, 0);
        SHOP_PLANTS.forEach(p -> {
            this.purchasablePlants.put(new Pair<>(p.getKey().apply(samplePoint).getName(), p.getValue()),
                                   p.getKey());
        });

        //Move already bought plants from boughtPlants to boughtPlants
        getPlayerBoughtPlants().forEach(p -> {
            this.boughtPlants.put(p, this.purchasablePlants.get(p));
            this.purchasablePlants.remove(p);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Pair<String, Integer>, Boolean> getPurchasablePlants() {
        final Map<Pair<String, Integer>, Boolean> outputMap = new HashMap<>();
        this.purchasablePlants.keySet().forEach(k -> outputMap.put(k, false));
        this.boughtPlants.keySet().forEach(k -> outputMap.put(k, true));
        return outputMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyPlant(final Pair<String, Integer> plantInfo) {
        //TODO: refactor
        if(this.purchasablePlants.containsKey(plantInfo) &&
                this.player.subtractCoins(plantInfo.getValue())) {
            this.player.addPlant(getKeyIndex(plantInfo));
            this.purchasablePlants.remove(plantInfo);
            this.boughtPlants.put(plantInfo, this.purchasablePlants.get(plantInfo));
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Function<Point2D, Plant>> getBoughtPlantsFunctions() {
        return new HashSet<>(this.boughtPlants.values());
    }

    private Set<Pair<String, Integer>> getPlayerBoughtPlants() {
        int i = 0;
        Set<Pair<String, Integer>> boughtPlants = new HashSet<>();
        for (var p : this.purchasablePlants.entrySet()) {
            if(this.player.getPlantsIds().contains(i)) {
                boughtPlants.add(p.getKey());
            }
            i++;
        }
        return boughtPlants;
    }
    private int getKeyIndex(final Pair<String, Integer> plantInfo) {
        int i = 0;
        for (var e: this.purchasablePlants.entrySet()) {
            if (e.equals(plantInfo)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}

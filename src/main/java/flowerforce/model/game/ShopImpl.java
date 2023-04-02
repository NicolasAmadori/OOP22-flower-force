package flowerforce.model.game;

import flowerforce.model.entities.CherryBomb;
import flowerforce.model.entities.ExplodingPlant;
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
            new Pair<> ((pos) -> new CherryBomb(pos), 20)
    ); //TODO: set actual costs
    private final Player player;
    private final Map<Pair<String, Integer>, Function<Point2D, Plant>> buyablePlants = new LinkedHashMap<>();

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
            this.buyablePlants.put(new Pair<>(p.getKey().apply(samplePoint).getName(), p.getValue()),
                                   p.getKey());
        });

        //Move already bought plants from buyablePlants to boughtPlants
        getPlayerBoughtPlants().forEach(p -> {
            this.boughtPlants.put(p, this.buyablePlants.get(p));
            this.buyablePlants.remove(p);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Pair<String, Integer>, Boolean> getPlants() {
        final Map<Pair<String, Integer>, Boolean> outputMap = new HashMap<>();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Function<Point2D, Plant>> getBoughtPlantsProducer() {
        return  this.boughtPlants.values().stream()
                .collect(Collectors.toSet());
    }

    private Set<Pair<String, Integer>> getPlayerBoughtPlants() {
        int i = 0;
        Set<Pair<String, Integer>> boughtPlants = new HashSet<>();
        for (var p : this.buyablePlants.entrySet()) {
            if(this.player.getPlantsIds().contains(i)) {
                boughtPlants.add(p.getKey());
            }
            i++;
        }
        return boughtPlants;
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

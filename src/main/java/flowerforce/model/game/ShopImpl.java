package flowerforce.model.game;

import flowerforce.model.entities.CherryBomb;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.SunflowerFactoryImpl;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;

/**
 * This is an implementation of {@link Shop}
 */
public class ShopImpl implements Shop{
    private static final List<Pair<Function<Point2D, Plant>, Integer>> SHOP_PLANTS = List.of(
            new Pair<Function<Point2D, Plant>, Integer> ((pos) -> new SunflowerFactoryImpl().createDoubleSunflower(pos), 600),
            new Pair<Function<Point2D, Plant>, Integer> (CherryBomb::new, 900)
    );
    private final Player player;

    private final List<Pair<String, Integer>> plants = new ArrayList<>();
//    private final Map<Function<Point2D, Plant>, Pair<String, Integer>> plants = new LinkedHashMap<>();

    /**
     * This is a constructor for a new shop instance.
     * @param player The player to add bought plants to
     */
    public ShopImpl(Player player) {
        this.player = player;

        //Adding all plants in the map
        final Point2D samplePoint = new Point2D(0, 0);
        SHOP_PLANTS.forEach(p -> {
            this.plants.add(
                    new Pair<>(p.getKey().apply(samplePoint).getName(), p.getValue()));
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Pair<String, Integer>, Boolean> getPurchasablePlants() {
        final Map<Pair<String, Integer>, Boolean> outputMap = new HashMap<>();
        var playerPlants = this.getPlayerBoughtPlants();
        this.plants.forEach(p -> outputMap.put(p, !playerPlants.contains(p) && this.player.getCoins() >= p.getValue()));
        return outputMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyPlant(final Pair<String, Integer> plantInfo) {
        //TODO: refactor
        if(this.plants.contains(plantInfo) &&
                this.player.subtractCoins(plantInfo.getValue())) {
            this.player.addPlant(getKeyIndex(plantInfo));
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Function<Point2D, Plant>> getBoughtPlantsFunctions() {
        return this.player.getPlantsIds().stream()
                .map(id -> SHOP_PLANTS.get(id).getKey())
                .collect(Collectors.toSet());
    }

    private Set<Pair<String, Integer>> getPlayerBoughtPlants() {
        return this.player.getPlantsIds().stream()
                .map(id -> this.plants.get(id))
                .collect(Collectors.toSet());
    }
    private int getKeyIndex(final Pair<String, Integer> plantInfo) {
        return this.plants.indexOf(plantInfo);
    }
}

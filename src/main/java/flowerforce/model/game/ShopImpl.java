package flowerforce.model.game;
import flowerforce.model.entities.IdConverter;
import javafx.util.Pair;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This is an implementation of {@link Shop}
 */
public class ShopImpl implements Shop{
    private Player player;
    private Map<IdConverter.Plants, Pair<Integer, Boolean>> buyablePlants;

    /**
     * This is a constructor for a new shop instance.
     * @param player The player to add bought plants to
     */
    public ShopImpl(Player player) {
        this.player = player;
        buyablePlants = Map.of(IdConverter.Plants.CHERRYBOMB, new Pair<>(300, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Pair<Integer, Boolean>> getPlants() {
        return this.buyablePlants.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().ordinal(),
                        e -> e.getValue()
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyPlant(int id) {
        if(this.buyablePlants.containsKey(IdConverter.Plants.values()[id])) {
            if(this.player.subtractCoins(IdConverter.Plants.values()[id].getCost())) {
                this.player.addPlant(IdConverter.Plants.values()[id]);//TODO: merge subtractCoins and addPlant
                this.buyablePlants.compute(IdConverter.Plants.values()[id], (k, v) -> new Pair<>(v.getKey(), true));
                return true;
            }
        }
        return false;
    }
}

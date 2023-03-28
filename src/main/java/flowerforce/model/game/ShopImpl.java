package flowerforce.model.game;
import flowerforce.model.entities.IdConverter;
import java.util.Map;

public class ShopImpl implements Shop{
    private Player player;
    private Map<BuyablePlant, Boolean> buyablePlants;

    public ShopImpl(Player player) {
        this.player = player;
        buyablePlants = Map.of(new BuyablePlant(IdConverter.Plants.FASTSHOOTER, 200), false);
    }
    @Override
    public Map<BuyablePlant, Boolean> getPlants() {
        return this.buyablePlants.entrySet().stream()
                .map(e -> new Map.Entry<>() {});
    }

    @Override
    public boolean buyPlant(int id) {
        BuyablePlant boughtPlant = null;
        for (var entry : this.buyablePlants.entrySet()) {
            if(entry.getKey().getId() == id && entry.getKey().getCost() <= this.player.getCoins()) {
                boughtPlant = entry.getKey();
            }
        }
        if(boughtPlant.equals(null)) {
            return false;
        }
        this.player.subtractCoins(boughtPlant.getCost());
        this.player.addPlant(boughtPlant.getId());
        this.buyablePlants.put(boughtPlant, true);
        return true;
    }
}

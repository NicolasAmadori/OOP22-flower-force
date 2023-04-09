package flowerforce.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flowerforce.model.entities.plants.PlantInfo;
import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;
import flowerforce.model.game.Shop;
import flowerforce.model.game.ShopImpl;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class TestShop {

    private static final int COINS1 = 600;
    private static final int COINS2 = 400;
    private static final int COINS3 = 200;
    private static final int COINS4 = 1_000;
    private Player player = new PlayerImpl();
    private Shop shop = new ShopImpl(this.player);

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setup() {
        this.player = new PlayerImpl(); //The default player has 0 coins
        this.shop = new ShopImpl(this.player);
    }

    /**
     * Test the getPurchasablePlants method.
     */
    @Test
    void testPurchasablePlantNumber() {
        Set<PlantInfo> purchasablePlants = this.shop.getPlants().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        //when the player has 0 coins, the number of purchasable plants must be 0
        assertEquals(0, purchasablePlants.size());

        this.player.addCoins(COINS1); //Add enough coins to buy the doublesunflower
        purchasablePlants = getPurchasablePlants();
        //when the player has 600 coins, the number of purchasable plants must be 1 (double sunflower)
        assertEquals(1, purchasablePlants.size());

        this.player.addCoins(COINS2); //Add enough coins to the player to buy the cherrybomb
        purchasablePlants = getPurchasablePlants();
        //when the player has 1000 coins, the number of purchasable plants must be 2 (double sunflower and cherrybomb)
        assertEquals(2, purchasablePlants.size());

        this.player.addCoins(COINS3); //Add enough coins to the player to buy the strongshooter
        purchasablePlants = getPurchasablePlants();
        //when the player has 1200 coins, the number of purchasable plants
        // must be 3 (double sunflower, cherrybomb and strongshooter)
        assertEquals(3, purchasablePlants.size());

        this.shop.buyPlant(purchasablePlants.stream().findAny().get()); //buy one of the 3 purchasable plants
        purchasablePlants = getPurchasablePlants();
        //the number of purchasable plants must be 0,
        //because one is bought and for the other there can't be enough coins remaining
        assertEquals(0, purchasablePlants.size());

        this.player.addCoins(COINS4); //Add enough coins to the player to buy every plant
        purchasablePlants = getPurchasablePlants();
        //the number of purchasable plants should be 2 because 1 is already bought, and the other are available
        assertEquals(2, purchasablePlants.size());

    }

    /**
     * Test the purchase of a new plant.
     */
    @Test
    void testPurchase() {
        this.player.addCoins(COINS4);
        final PlantInfo anyPurchasablePlant = getPurchasablePlants().stream()
                                                            .findAny()
                                                            .get();

        //The method should return true because the player have enough money to buy the plant
        assertTrue(this.shop.buyPlant(anyPurchasablePlant));

        //Check if the plant is really bought (must be false because the plant is NOT purchasable)
        assertFalse(this.shop.getPlants().get(anyPurchasablePlant));

        //the method should return false because the player already have bought that plant
        assertFalse(this.shop.buyPlant(anyPurchasablePlant));
    }

    private Set<PlantInfo> getPurchasablePlants() {
        return this.shop.getPlants().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}

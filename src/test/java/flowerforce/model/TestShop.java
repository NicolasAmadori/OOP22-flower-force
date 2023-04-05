package flowerforce.model;

import flowerforce.model.entities.PlantInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;
import flowerforce.model.game.Shop;
import flowerforce.model.game.ShopImpl;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class TestShop {

    private static final int COINS1 = 600;
    private static final int COINS2 = 300;
    private static final int COINS3 = 1_000;
    private Player player;
    private Shop shop;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        this.player = new PlayerImpl(); //The default player has 0 coins
        this.shop = new ShopImpl(this.player);
    }

    /**
     * Test the getPurchasablePlants method.
     */
    @Test
    void testPurchasablePlantNumber() {
        Set<PlantInfo> purchasablePlants = this.shop.getPurchasablePlants().entrySet().stream()
                .filter(e -> e.getValue())
                 .map(e -> e.getKey())
                .collect(Collectors.toSet());
        //when the player has 0 coins, the number of purchasable plants must be 0
        assertEquals(0, purchasablePlants.size());

        this.player.addCoins(COINS1); //Add enough coins to buy the doublesunflower
        purchasablePlants = this.shop.getPurchasablePlants().entrySet().stream()
                .filter(e -> e.getValue())
                .map(e -> e.getKey())
                .collect(Collectors.toSet());
        //when the player has 600 coins, the number of purchasable plants must be 1 (double sunflower)
        assertEquals(1, purchasablePlants.size());

        this.player.addCoins(COINS2); //Add enough coins to the player to buy the cherrybomb
        purchasablePlants = this.shop.getPurchasablePlants().entrySet().stream()
                .filter(e -> e.getValue())
                .map(e -> e.getKey())
                .collect(Collectors.toSet());
        //when the player has 900 coins, the number of purchasable plants must be 2 (double sunflower and cherrybomb)
        assertEquals(2, purchasablePlants.size());

        this.shop.buyPlant(purchasablePlants.stream().findAny().get()); //buy one of the 2 purchasable plants
        purchasablePlants = this.shop.getPurchasablePlants().entrySet().stream()
                .filter(e -> e.getValue())
                .map(e -> e.getKey())
                .collect(Collectors.toSet());
        //the number of purchasable plants must be 0,
        //because one is bought and for the other there can't be enough coins remaining
        assertEquals(0, purchasablePlants.size());

        this.player.addCoins(COINS3); //Add enough coins to the player to buy the cherrybomb
        purchasablePlants = this.shop.getPurchasablePlants().entrySet().stream()
                .filter(e -> e.getValue())
                .map(e -> e.getKey())
                .collect(Collectors.toSet());
        //the number of purchasable plants should be 1 because 1 is already bought, and only the other is available
        assertEquals(1, purchasablePlants.size());

    }

    /**
     * Test the purchase of a new plant.
     */
    @Test
    void testPurchase() {
        this.player.addCoins(COINS3);
        final PlantInfo anyPurchasablePlant = this.shop.getPurchasablePlants().entrySet().stream()
                                                            .filter(e -> e.getValue())
                                                            .map(e -> e.getKey())
                                                            .findAny()
                                                            .get();

        //The method should return true because now the player have enough money to buy the plant
        assertTrue(this.shop.buyPlant(anyPurchasablePlant));

        assertTrue(this.shop.getPurchasablePlants().get(anyPurchasablePlant)); //Check if the plant is really bought

        //the method should return false because the player already have bought that plant
        assertFalse(this.shop.buyPlant(anyPurchasablePlant));
    }
}

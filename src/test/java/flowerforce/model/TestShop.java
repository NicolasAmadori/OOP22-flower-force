package flowerforce.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;
import flowerforce.model.game.Shop;
import flowerforce.model.game.ShopImpl;

final class TestShop {

    Player player;
    Shop shop;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        player = new PlayerImpl(); //The default player has 0 coins
        shop = new ShopImpl(player);
    }

    /**
     * Test the purchase of a new plant.
     */
    @Test
    void testPurchase() {
        var plantToBuy = this.shop.getPurchasablePlants().entrySet().stream()
                                                                                .filter(e -> e.getValue())
                                                                                .map(e -> e.getKey())
                                                                                .findAny()
                                                                                .get();

        assertFalse(this.shop.buyPlant(plantToBuy));//the method should return false because the player do not have enough money
        assertFalse(this.shop.getPurchasablePlants().get(plantToBuy));//Check that the plant is not bough

        this.player.addCoins(1000);//Add coins to the player to buy plants

        assertTrue(this.shop.buyPlant(plantToBuy));//The method should return true because now the player have enough money to buy the plant
        assertTrue(this.shop.getPurchasablePlants().get(plantToBuy));//Check if the plant is really bought

        assertFalse(this.shop.buyPlant(plantToBuy));//the method should return false because the player already have bought that plant
    }
}

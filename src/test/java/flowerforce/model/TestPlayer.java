package flowerforce.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class TestPlayer {

    Player player;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        player = new PlayerImpl(); //The default player has 0 coins
    }

    /**
     * Test the player's coins management
     */
    @Test
    void testCoins() {
        assertEquals(0, this.player.getCoins());

        this.player.addCoins(100);
        assertEquals(100, this.player.getCoins());

        assertTrue(this.player.subtractCoins(50));
        assertEquals(50, this.player.getCoins());

        assertFalse(this.player.subtractCoins(100));
        assertEquals(50, this.player.getCoins());
    }

    /**
     * Test the player's score management
     */
    @Test
    void testScore() {
        assertEquals(0, this.player.getScoreRecord());

        this.player.addNewScore(4_000);
        assertEquals(4_000, this.player.getCoins());

        this.player.addNewScore(1_500);
        assertEquals(4_000, this.player.getCoins());

        this.player.addNewScore(10_000);
        assertEquals(10_000, this.player.getCoins());
    }
}

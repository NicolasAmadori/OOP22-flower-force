package flowerforce.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class TestPlayer {

    private static final int STARTING_COINS = 0;
    private static final int COINS1 = 50;
    private static final int COINS2 = 100;
    private static final int SCORE1 = 4_000;
    private static final int SCORE2 = 1_500;
    private static final int SCORE3 = 10_000;

    private Player player;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        player = new PlayerImpl(); //The default player has 0 coins
    }

    /**
     * Test the player's coins management.
     */
    @Test
    void testCoins() {
        assertEquals(STARTING_COINS, this.player.getCoins());

        this.player.addCoins(COINS2);
        assertEquals(COINS2, this.player.getCoins());

        assertTrue(this.player.subtractCoins(COINS1));
        assertEquals(COINS1, this.player.getCoins());

        assertFalse(this.player.subtractCoins(COINS2));
        assertEquals(COINS1, this.player.getCoins());
    }

    /**
     * Test the player's score management.
     */
    @Test
    void testScore() {
        assertEquals(STARTING_COINS, this.player.getScoreRecord());

        this.player.addNewScore(SCORE1);
        assertEquals(SCORE1, this.player.getScoreRecord());

        this.player.addNewScore(SCORE2);
        assertEquals(SCORE1, this.player.getScoreRecord());

        this.player.addNewScore(SCORE3);
        assertEquals(SCORE3, this.player.getScoreRecord());
    }
}

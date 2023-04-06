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
    private static final int FIRST_SCORE_TO_ADD = 4_000;
    private static final int SECOND_SCORE_TO_ADD = 1_500;
    private static final int THIRD_SCORE_TO_ADD = 10_000;

    private Player player = new PlayerImpl();

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
        int playerCoins = STARTING_COINS;
        //at first the player coins must be 0
        assertEquals(playerCoins, this.player.getCoins());

        //you should add 100 coins with no problem
        //CHECKSTYLE: MagicNumber OFF
        //100 is a random number
        this.player.addCoins(100);
        playerCoins += 100;
        //CHECKSTYLE: MagicNumber OFF
        assertEquals(playerCoins, this.player.getCoins());

        //you should subtract 50 from 100 with no problem
        //CHECKSTYLE: MagicNumber OFF
        //50 is a random number
        assertTrue(this.player.subtractCoins(50));
        playerCoins -= 50;
        //CHECKSTYLE: MagicNumber ON
        assertEquals(playerCoins, this.player.getCoins());

        //you shouldn't be able to subtract 70 from 50 remaining coins
        //CHECKSTYLE: MagicNumber OFF
        //70 is a random number
        assertFalse(this.player.subtractCoins(70));
        //CHECKSTYLE: MagicNumber ON
        assertEquals(playerCoins, this.player.getCoins());
    }

    /**
     * Test the player's score management.
     */
    @Test
    void testScore() {
        int playerScoreRecord = 0;
        //The initial score record must be 0
        assertEquals(playerScoreRecord, this.player.getScoreRecord());

        //Adding a score of 4000, 4000 must be the new score record
        this.player.addNewScore(FIRST_SCORE_TO_ADD);
        playerScoreRecord = Math.max(playerScoreRecord, FIRST_SCORE_TO_ADD);
        assertEquals(playerScoreRecord, this.player.getScoreRecord());

        //adding a score of 1500, 4000 must still be the score record
        this.player.addNewScore(SECOND_SCORE_TO_ADD);
        playerScoreRecord = Math.max(playerScoreRecord, SECOND_SCORE_TO_ADD);
        assertEquals(playerScoreRecord, this.player.getScoreRecord());

        //adding a score of 10000, 10000 must be the new score record
        this.player.addNewScore(THIRD_SCORE_TO_ADD);
        playerScoreRecord = Math.max(playerScoreRecord, THIRD_SCORE_TO_ADD);
        assertEquals(playerScoreRecord, this.player.getScoreRecord());
    }
}

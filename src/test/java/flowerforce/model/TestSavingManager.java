package flowerforce.model;

import flowerforce.controller.utilities.SaveManager;
import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TestSavingManager {

    private static final String FILE_NAME = "player";
    private Player player;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setup() {
        this.player = new PlayerImpl(); //The default player has 0 coins
    }

    /**
     * Test the player saving.
     */
    @Test
    void testSaving() {
        final SaveManager<Player> playerSaveManager = new SaveManager(
                PlayerImpl.class,
                FILE_NAME);
        playerSaveManager.save(this.player);

        final Optional<Player> newPlayer = playerSaveManager.load();
        assertTrue(newPlayer.isPresent());


        //check that all parameters are the same (player instances of course do NOT are equals)
        assertEquals(this.player.getCoins(), newPlayer.get().getCoins());
        assertEquals(this.player.getScoreRecord(), newPlayer.get().getScoreRecord());
        assertEquals(this.player.getPlantsIds(), newPlayer.get().getPlantsIds());
        assertEquals(this.player.getLastUnlockedLevelId(), newPlayer.get().getLastUnlockedLevelId());
    }
}

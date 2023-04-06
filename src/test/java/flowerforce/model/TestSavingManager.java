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
    private Player player = new PlayerImpl();

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        player = new PlayerImpl(); //The default player has 0 coins
    }

    /**
     * Test the player saving.
     */
    @Test
    void testSaving() {
        final SaveManager<PlayerImpl> playerSaveManager = new SaveManager<>(PlayerImpl.class, FILE_NAME);

        //random operation to modify player values
        this.player.addCoins(160);
        this.player.addNewScore(56560);
        this.player.unlockedNextLevel();
        this.player.unlockedNextLevel();
        playerSaveManager.save((PlayerImpl) this.player); //cast to save the player

        final Optional<PlayerImpl> newPlayer = playerSaveManager.load();
        assertTrue(newPlayer.isPresent());


        //check that all parameters are the same (player instances of course do NOT are equals)
        assertEquals(this.player.getCoins(), newPlayer.get().getCoins());
        assertEquals(this.player.getScoreRecord(), newPlayer.get().getScoreRecord());
        assertEquals(this.player.getPlantsIds(), newPlayer.get().getPlantsIds());
        assertEquals(this.player.getLastUnlockedLevelId(), newPlayer.get().getLastUnlockedLevelId());
    }
}

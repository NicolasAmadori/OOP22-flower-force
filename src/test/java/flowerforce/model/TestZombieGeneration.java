package flowerforce.model;

import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;
import flowerforce.model.game.ZombieGenerationLevelImpl;
import flowerforce.model.game.ZombieGenerationInfiniteImpl;
import flowerforce.model.utilities.RenderingInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


final class TestZombieGeneration {
    private ZombieGenerationLevelImpl zombieGen;
    private ZombieGenerationInfiniteImpl zombieGenInfinite;
    private static final double STANDARD_SECS_SPAWN_ZOMBIE = 15.0;
    private static final int TIME_TO_SPAWN_ZOMBIE = (int)
            (STANDARD_SECS_SPAWN_ZOMBIE * RenderingInformation.getFramesPerSecond());
    private static final int FIRST_ZOMBIE_HORDE = 13;
    private static final int SECOND_ZOMBIE_HORDE = 18;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        final Player player = new PlayerImpl();
        zombieGen = new ZombieGenerationLevelImpl(player.getLastUnlockedLevelId());
        zombieGenInfinite = new ZombieGenerationInfiniteImpl(player.getLastUnlockedLevelId());
    }

    /**
     * Test the zombie generation.
     */
    @Test
    void testZombieGeneration() {
        assertEquals(1, this.zombieGen.getSpawnedZombie());
        for (int i = 0; i < TIME_TO_SPAWN_ZOMBIE; i++) {
            zombieGen.zombieGeneration();
        }
        assertEquals(2, this.zombieGen.getSpawnedZombie());
        for (int i = 0; i < TIME_TO_SPAWN_ZOMBIE; i++) {
            zombieGen.zombieGeneration();
        }
        assertEquals(3, this.zombieGen.getSpawnedZombie());
    }

    /**
     * Test the increase in the number of horde zombies.
     */
    @Test
    void testIncreaseHorde() {
        assertEquals(FIRST_ZOMBIE_HORDE, this.zombieGenInfinite.getNumberHordeZombie());

        while (this.zombieGenInfinite.getSpawnedZombie() != FIRST_ZOMBIE_HORDE) {
            zombieGenInfinite.zombieGeneration();
        }

        assertEquals(SECOND_ZOMBIE_HORDE, this.zombieGenInfinite.getNumberHordeZombie());
    }
}

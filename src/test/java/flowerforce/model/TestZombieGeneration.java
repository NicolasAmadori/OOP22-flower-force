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
        //at the beginning there will be 0 zombie spawned
        assertEquals(0, this.zombieGen.getSpawnedZombie() - 1);


        for (int i = 0; i < TIME_TO_SPAWN_ZOMBIE; i++) {
            zombieGen.zombieGeneration();
        }
        //after waiting this amount of time, total spawned zombies will be 2
        assertEquals(1, this.zombieGen.getSpawnedZombie() - 1);

        for (int i = 0; i < TIME_TO_SPAWN_ZOMBIE; i++) {
            zombieGen.zombieGeneration();
        }
        //after waiting this amount of time, total spawned zombies will be 2
        assertEquals(2, this.zombieGen.getSpawnedZombie() - 1);
    }

    /**
     * Test the increase in the number of horde zombies.
     */
    @Test
    void testIncreaseHorde() {
        //the first wave of zombies consists of 8 zombies + 5 horde zombies
        assertEquals(FIRST_ZOMBIE_HORDE, this.zombieGenInfinite.getNumberHordeZombie());

        while (this.zombieGenInfinite.getSpawnedZombie() != FIRST_ZOMBIE_HORDE) {
            zombieGenInfinite.zombieGeneration();
        }

        //the second wave of zombies consists of 8 zombies + 10 horde zombies,
        //because the horde increases by 5
        assertEquals(SECOND_ZOMBIE_HORDE, this.zombieGenInfinite.getNumberHordeZombie());
    }
}

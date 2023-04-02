package flowerforce.model.game;

import flowerforce.model.entities.Zombie;

import java.util.Optional;

/**
 * Models the game zombie generation in a level game.
 */
public interface ZombieGenerationLevel extends ZombieGeneration {

    /**
     * @return the boss of the level
     */
    Zombie bossGeneration();
}
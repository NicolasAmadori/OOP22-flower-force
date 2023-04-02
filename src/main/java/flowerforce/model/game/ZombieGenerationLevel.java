package flowerforce.model.game;

import flowerforce.model.entities.Zombie;

import java.util.Optional;

/**
 * Models the game zombie generation in a level game.
 */
public interface ZombieGenerationLevel extends ZombieGeneration {

    /**
     * If boss is present, spawns it.
     * @return the boss if it is present
     */
    Optional<Zombie> bossGeneration();
}
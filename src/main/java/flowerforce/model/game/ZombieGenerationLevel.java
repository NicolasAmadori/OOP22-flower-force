package flowerforce.model.game;

import javafx.geometry.Point2D;

import java.util.function.Function;

import flowerforce.model.entities.zombies.Zombie;

/**
 * Models the game zombie generation in a level game.
 */
public interface ZombieGenerationLevel extends ZombieGeneration {

    /**
     * @param boss to generate
     * @return the boss of the level
     */
    Zombie bossGeneration(Function<Point2D, Zombie> boss);
}

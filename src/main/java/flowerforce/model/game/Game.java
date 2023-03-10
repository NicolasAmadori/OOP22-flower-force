package flowerforce.model.game;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.Set;

/**
 * Models the development of the game.
 */
public interface Game {
    /**
     * Call to update the game status.
     */
    void update();

    /**
     * Call to know which zombies are still alive.
     * @return the set of zombie still alive
     */
    Set<Zombie> getZombies();

    /**
     * Call to know which zombies are still alive.
     * @return the set of plants still alive
     */
    Set<Plant> getPlants();

    /**
     * Call to know which zombies are eating.
     * @return the list of zombie that are eating
     */
    List<Zombie> getZombieEating();

    /**
     * Call to know which bullets are in the game field.
     * @return the set of bullets in the game field
     */
    Set<Bullet> getBullet();

    /**
     *
     * @return The number of suns the player has in the game
     */
    Integer getSun();

    /**
     * If possible, place a plant in the field.
     * @param idPlant Plant type ID to place
     * @param position Coordinates of the plant to be placed
     * @return True if the plant has been placed
     */
    boolean placePlant(IdConverter.Plants idPlant, Point2D position);

    /**
     * Determine if the game has ended.
     * @return True if the game has ended
     */
    boolean isOver();

    /**
     * Identify which plants can be selected to be placed on the playing field.
     * @return the types of plants that can be selected
     */
    Set<IdConverter.Plants> availablePlants();

    /**
     *
     * @return true if the player won the level.
     */
    boolean result();
}

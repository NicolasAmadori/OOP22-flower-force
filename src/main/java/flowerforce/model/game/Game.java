package flowerforce.model.game;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import javafx.geometry.Point2D;

import java.util.List;

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
     * @return the list of zombie still alive
     */
    List<Zombie> getZombies();

    /**
     * Call to know which zombies are still alive.
     * @return the list of plants still alive
     */
    List<Plant> getPlants();

    /**
     * Call to know which zombies are eating.
     * @return the list of zombie that are eating
     */
    List<Zombie> getZombieEating();

    /**
     * Call to know which bullets are in the game field.
     * @return the list of bullets in the game field
     */
    List<Bullet> getBullet();

    /**
     * If possible, place a plant in the field.
     * @param idPlant Plant type ID to place
     * @param position Coordinates of the plant to be placed
     * @return True if the plant has been placed
     */
    boolean placePlant(int idPlant, Point2D position);

    /**
     * Determine if the game has ended.
     * @return True if the game has ended
     */
    boolean isOver();
}

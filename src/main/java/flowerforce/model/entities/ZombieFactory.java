package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Zombies;
import javafx.geometry.Point2D;

/**
 * Factory of different istances of {@link Zombie}.
 */
public interface ZombieFactory {

    /**
     * @param position where it is initially placed
     * @param zombieType the type of zombie
     * @return a basic zombie
     */
    Zombie basic(Point2D position, Zombies zombieType);

    /** 
     * @param position where it is initially placed
     * @param zombieType the type of zombie
     * @return a medium-resistance zombie
     */
    Zombie conehead(Point2D position, Zombies zombieType);

    /**
     * @param position where it is initially placed
     * @param zombieType the type of zombie
     * @return a high-resistance zombie
     */
    Zombie buckethead(Point2D position, Zombies zombieType);

    /**
     * @param position where it is initially placed
     * @param zombieType the type of zombie
     * @return a running zombie
     */
    Zombie running(Point2D position, Zombies zombieType);

    /** 
     * @param position where it is initially placed
     * @param zombieType the type of zombie
     * @return a running and high-resistance zombie
     */
    Zombie quarterback(Point2D position, Zombies zombieType);

    /**
     * @param position where it is initially placed
     * @param zombieType the type of zombie
     * @return a medium resistance zombie, that starts running after its newspaper gets destroyed
     */
    Zombie newspaper(Point2D position, Zombies zombieType);
}

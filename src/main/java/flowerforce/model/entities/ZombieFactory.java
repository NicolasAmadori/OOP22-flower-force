package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Model a factory of different zombies
 */
public interface ZombieFactory {

    /**
     * @param position where it is initially placed
     * @return a basic zombie
     */
    Zombie basic(final Point2D position);

    /** 
     * @param position where it is initially placed
     * @return a medium-resistance zombie
     */
    Zombie conehead(final Point2D position);

    /**
     * @param position where it is initially placed 
     * @return a high-resistance zombie
     */
    Zombie buckethead(final Point2D position);

    /**
     * @param position where it is initially placed
     * @return a running zombie
     */
    Zombie running(final Point2D position);

    /** 
     * @param position where it is initially placed
     * @return a running and high-resistance zombie
     */
    Zombie quarterback(final Point2D position);
    
}

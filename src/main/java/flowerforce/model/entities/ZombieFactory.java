package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Factory of different istances of {@link Zombie}.
 */
public interface ZombieFactory {

    /**
     * @param position where it is initially placed
     * @return a basic zombie
     */
    Zombie basic(Point2D position);

    /** 
     * @param position where it is initially placed
     * @return a medium-resistance zombie
     */
    Zombie conehead(Point2D position);

    /**
     * @param position where it is initially placed
     * @return a high-resistance zombie
     */
    Zombie buckethead(Point2D position);

    /**
     * @param position where it is initially placed
     * @return a running zombie
     */
    Zombie runner(Point2D position);

    /** 
     * @param position where it is initially placed
     * @return a running and high-resistance zombie
     */
    Zombie quarterback(Point2D position);

    /**
     * @param position where it is initially placed
     * @return a medium resistance zombie, that starts running after its newspaper gets destroyed
     */
    Zombie newspaper(Point2D position);

    /**
     * @param position where it is initially placed
     * @return an enormous zombie, with enormous damage and resistance
     */
    Zombie gargantuar(Point2D position);
}

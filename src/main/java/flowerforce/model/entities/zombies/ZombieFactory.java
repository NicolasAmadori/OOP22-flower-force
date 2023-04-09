package flowerforce.model.entities.zombies;

import flowerforce.model.utilities.RenderingInformation;
import javafx.geometry.Point2D;

/**
 * Static factory of different types of {@link Zombie}.
 */
public final class ZombieFactory {

    private static final int BASIC_HEALTH = 181;
    private static final int BASIC_DAMAGE = 100;
    private static final int CONE_HEALTH = 370;
    private static final int BUCKET_HEALTH = 950;
    private static final int HELMET_HEALTH = 1250;
    private static final int NEWSPAPER_HEALTH = 200;
    private static final int GARGANTUAR_HEALTH = 3000;
    private static final int GARGANTUAR_DAMAGE = 10_000;
    private static final double BASIC_SECS_PER_CELL = 4.7;
    private static final double BASIC_DELTA = RenderingInformation.getDeltaFromSecondsPerCell(BASIC_SECS_PER_CELL);
    private static final double RUNNING_DELTA = 2 * BASIC_DELTA;
    private static final int BASIC_DIFFICULTY = 1;
    private static final int CONEHEAD_DIFFICULTY = 2;
    private static final int RUNNING_DIFFICULTY = 3;
    private static final int NEWSPAPER_DIFFICULTY = 3;
    private static final int BUCKETHEAD_DIFFICULTY = 4;
    private static final int QUARTERBACK_DIFFICULTY = 6;
    private static final int GARGANTUAR_DIFFICULTY = 10;

    private ZombieFactory() {
    }

    /**
     * @param position where it is initially placed
     * @return a basic zombie
     */
    public static Zombie basic(final Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position, BASIC_DIFFICULTY, "basic");
    }

    /** 
     * @param position where it is initially placed
     * @return a medium-resistance zombie
     */
    public static Zombie conehead(final Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH + CONE_HEALTH, position, CONEHEAD_DIFFICULTY, "conehead");
    }

    /**
     * @param position where it is initially placed
     * @return a high-resistance zombie
     */
    public static Zombie buckethead(final Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH + BUCKET_HEALTH, position,
            BUCKETHEAD_DIFFICULTY, "buckethead");
    }

    /**
     * @param position where it is initially placed
     * @return a running zombie
     */
    public static Zombie runner(final Point2D position) {
        return new ZombieImpl(RUNNING_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position, RUNNING_DIFFICULTY, "runner");
    }

    /** 
     * @param position where it is initially placed
     * @return a running and high-resistance zombie
     */
    public static Zombie quarterback(final Point2D position) {
        return new ZombieImpl(RUNNING_DELTA, BASIC_DAMAGE, BASIC_HEALTH + HELMET_HEALTH, position,
            QUARTERBACK_DIFFICULTY, "quarterback");
    }

    /**
    * @param position where it is initially placed
    * @return a medium resistance zombie, that starts running after its newspaper gets destroyed
    */
    public static Zombie newspaper(final Point2D position) {
        return new NewspaperZombie(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position, 
            NEWSPAPER_HEALTH, NEWSPAPER_DIFFICULTY, "newspaper");
    }

    /**
     * @param position where it is initially placed
     * @return an enormous zombie, with enormous damage and resistance
     */
    public static Zombie gargantuar(final Point2D position) {
        return new ZombieImpl(BASIC_DELTA, GARGANTUAR_DAMAGE, GARGANTUAR_HEALTH, position, GARGANTUAR_DIFFICULTY, "gargantuar");
    }

}

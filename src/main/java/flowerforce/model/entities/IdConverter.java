package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * This functional class can be used to obtain an {@link Entity} from its type identificator.
 */
public final class IdConverter {

    private static final ZombieFactory ZOMBIE_FACTORY = new ZombieFactoryImpl();
    private static final ShootingPlantFactory SHOOTING_PLANT_FACTORY = new ShootingPlantFactoryImpl();

    private IdConverter() {
    }

    /**
     * Different plant types.
     */
    public enum Plants {
        /**
         * Produces suns.
         */
        SUNFLOWER,
        /**
         * Shoots standard bullets.
         */
        COMMONSHOOTER,
        /**
         * Shoots special bullets that freeze zombies.
         */
        SNOWSHOOTER,
        /**
         * Shoots standard bullets with an high firing rate.
         */
        FASTSHOOTER,
        /**
         * Shoots special bullets that unfreeze zombies but gives them higher damage. 
         */
        FIRESHOOTER;
    }

    /**
     * Different zombie types.
     */
    public enum Zombies {
        /**
         * Basic zombie, with default health and velocity.
         */
        BASIC(0),
        /**
         * Medium resistance zombie.
         */
        CONEHEAD(1),
        /**
         * High resistance zombie.
         */
        BUCKETHEAD(2),
        /**
         * High velocity zombie.
         */
        RUNNER(1),
        /**
         * High resistance and running zombie.
         */
        QUARTERBACK(3);

        private final int difficulty;

        Zombies(final int difficulty) {
            this.difficulty = difficulty;
        }

        /**
         * @return a generic difficulty of the zombie
         */
        public int getDifficulty() {
            return this.difficulty;
        } 
    }

    /**
     * 
     * @param plantType the type of plant
     * @param pos the position of the plant
     * @return a new placed plant of the specified type
     */
    public static Plant createPlant(final Plants plantType, final Point2D pos) {
        switch (plantType) {
            case SUNFLOWER:
                return new SunflowerImpl(pos, plantType);
            case COMMONSHOOTER:
                return SHOOTING_PLANT_FACTORY.common(pos, plantType);
            case SNOWSHOOTER:
                return SHOOTING_PLANT_FACTORY.snow(pos, plantType);
            case FASTSHOOTER:
                return SHOOTING_PLANT_FACTORY.fast(pos, plantType);
            case FIRESHOOTER:
                return SHOOTING_PLANT_FACTORY.fire(pos, plantType);
            default:
                throw new IllegalStateException("ERROR: plant type has not been identified");
        }
    }

    /**
     * 
     * @param zombieType the type of zombie
     * @param pos the position of the zombie
     * @return a new placed zombie of the specified type
     */
    public static Zombie createZombie(final Zombies zombieType, final Point2D pos) {
        switch (zombieType) {
            case BASIC:
                return ZOMBIE_FACTORY.basic(pos, zombieType);
            case CONEHEAD:
                return ZOMBIE_FACTORY.conehead(pos, zombieType);
            case BUCKETHEAD:
                return ZOMBIE_FACTORY.buckethead(pos, zombieType);
            case RUNNER:
                return ZOMBIE_FACTORY.running(pos, zombieType);
            case QUARTERBACK:
                return ZOMBIE_FACTORY.quarterback(pos, zombieType);
            default:
                throw new IllegalStateException("ERROR: zombie type has not been identified");
        }
    }

}

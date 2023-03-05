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
        BASIC,
        /**
         * Medium resistance zombie.
         */
        CONEHEAD,
        /**
         * High resistance zombie.
         */
        BUCKETHEAD,
        /**
         * High velocity zombie.
         */
        RUNNER,
        /**
         * High resistance and running zombie.
         */
        QUARTERBACK;

        public int getDifficulty() {
            return this.ordinal();
        }
    }

    /**
     * 
     * @param plantType the type of plant
     * @param pos the position of the plant
     * @return a new placed plant of the specified type
     */
    public static Plant createPlant(final IdConverter.Plants plantType, final Point2D pos) {
        switch (plantType) {
            case SUNFLOWER:
                return new SunflowerImpl(pos);
            case COMMONSHOOTER:
                return SHOOTING_PLANT_FACTORY.common(pos);
            case SNOWSHOOTER:
                return SHOOTING_PLANT_FACTORY.snow(pos);
            case FASTSHOOTER:
                return SHOOTING_PLANT_FACTORY.fast(pos);
            case FIRESHOOTER:
                return SHOOTING_PLANT_FACTORY.fire(pos);
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
    public static Zombie createZombie(final IdConverter.Zombies zombieType, final Point2D pos) {
        switch (zombieType) {
            case BASIC:
                return ZOMBIE_FACTORY.basic(pos);
            case CONEHEAD:
                return ZOMBIE_FACTORY.conehead(pos);
            case BUCKETHEAD:
                return ZOMBIE_FACTORY.buckethead(pos);
            case RUNNER:
                return ZOMBIE_FACTORY.running(pos);
            case QUARTERBACK:
                return ZOMBIE_FACTORY.quarterback(pos);
            default:
                throw new IllegalStateException("ERROR: zombie type has not been identified");
        }
    }

}

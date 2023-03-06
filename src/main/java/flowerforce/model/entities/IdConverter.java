package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * This functional class can be used to obtain an {@link Entity} from its type identificator.
 */
public final class IdConverter {

    private static final int SHORT_TIME = 300;
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
        SUNFLOWER(50, SHORT_TIME),
        /**
         * Shoots standard bullets.
         */
        COMMONSHOOTER(100, SHORT_TIME),
        /**
         * Shoots special bullets that freeze zombies.
         */
        SNOWSHOOTER(175, SHORT_TIME),
        /**
         * Shoots standard bullets with a high firing rate.
         */
        FASTSHOOTER(225, SHORT_TIME),
        /**
         * Shoots special bullets that unfreeze zombies but gives them higher damage. 
         */
        FIRESHOOTER(175, SHORT_TIME);

        private final int cost;
        private final int unlockTime;

        Plants(final int cost, final int unlockTime) {
            this.cost = cost;
            this.unlockTime = unlockTime;
        }

        /**
         *
         * @return the cost in suns of the plant
         */
        public int getCost() {
            return this.cost;
        }

        /**
         *
         * @return the time to wait for the plant to unlock
         */
        public int getUnlockTime() {
            return this.unlockTime;
        }
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

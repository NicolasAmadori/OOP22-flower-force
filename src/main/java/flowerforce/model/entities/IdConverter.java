package flowerforce.model.entities;

import flowerforce.model.utilities.RenderingInformation;
import javafx.geometry.Point2D;

/**
 * This functional class can be used to obtain an {@link Entity} from its type identificator.
 */
public final class IdConverter {

    private static final double FAST_RECHARGE_SECS = 7.5;
    private static final double SLOW_RECHARGE_SECS = 30;
    private static final double VERY_SLOW_RECHARGE_SECS = 30;
    private static final int FAST_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(FAST_RECHARGE_SECS);
    private static final int SLOW_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(SLOW_RECHARGE_SECS);
    private static final int VERY_SLOW_RECHARGE_TICKS = RenderingInformation.convertSecondsToCycles(VERY_SLOW_RECHARGE_SECS);
    private static final ZombieFactory ZOMBIE_FACTORY = new ZombieFactoryImpl();
    private static final ShootingPlantFactory SHOOTING_PLANT_FACTORY = new ShootingPlantFactoryImpl();
    private static final SunflowerFactory SUNFLOWER_FACTORY = new SunflowerFactoryImpl();
    private IdConverter() {
    }

    /**
     * Different plant types.
     */
    public enum Plants {
        /**
         * Produces suns.
         */
        SUNFLOWER(50, FAST_RECHARGE_TICKS),
        /**
         * Shoots standard bullets.
         */
        PEASHOOTER(100, FAST_RECHARGE_TICKS),
        /**
         * Shoots special bullets that freeze zombies.
         */
        SNOWSHOOTER(175, FAST_RECHARGE_TICKS),
        /**
         * Shoots standard bullets with a high firing rate.
         */
        FASTSHOOTER(225, FAST_RECHARGE_TICKS),
        /**
         * Shoots special bullets that unfreeze zombies but gives them higher damage. 
         */
        FIRESHOOTER(175, FAST_RECHARGE_TICKS),
        /**
         * Takes damage protecting other plants.
         */
        WALLNUT(50, SLOW_RECHARGE_TICKS),
        /**
         * Explodes giving a very high damage to all zombies inside a certain range.
         */
        CHERRYBOMB(150, VERY_SLOW_RECHARGE_TICKS),
        /**
         * Produces suns faster than a normal sunflower.
         */
        FASTSUNFLOWER(150, SLOW_RECHARGE_TICKS);

        private final int cost;
        private final int unlockTime;

        Plants(final int cost, final int unlockTime) {
            this.cost = cost;
            this.unlockTime = unlockTime;
        }

        public enum Bullets {
            /**
             * A standard bullet.
             */
            STANDARDBULLET,
            /**
             * A snow bullet that slows zombies.
             */
            SNOWBULLET,
            /**
             * A fire bullet that gives more damage than a standard bullet.
             */
            FIREBULLET;
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
        BASIC(1),
        /**
         * Medium resistance zombie.
         */
        CONEHEAD(2),
        /**
         * High resistance zombie.
         */
        BUCKETHEAD(4),
        /**
         * High velocity zombie.
         */
        RUNNER(3),
        /**
         * High resistance and running zombie.
         */
        QUARTERBACK(5),
        /**
         * Medium resistance zombie, starts running after its newspaper gets destroyed.
         */
        NEWSPAPER(3),
        /**
         * Enormous resistance zombie, with an enormous damage.
         */
        GARGANTUAR(10);

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
                return SUNFLOWER_FACTORY.createCommonSunflower(pos, plantType);
            case PEASHOOTER:
                return SHOOTING_PLANT_FACTORY.common(pos, plantType);
            case SNOWSHOOTER:
                return SHOOTING_PLANT_FACTORY.snow(pos, plantType);
            case FASTSHOOTER:
                return SHOOTING_PLANT_FACTORY.fast(pos, plantType);
            case FIRESHOOTER:
                return SHOOTING_PLANT_FACTORY.fire(pos, plantType);
            /*case WALLNUT:
                return new Nut(pos, plantType); */ //TODO: decomment
            case CHERRYBOMB:
                return new CherryBomb(pos, plantType);
            case FASTSUNFLOWER:
                return SUNFLOWER_FACTORY.createDoubleSunflower(pos, plantType);
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
            case NEWSPAPER:
                return ZOMBIE_FACTORY.newspaper(pos, zombieType);
            case GARGANTUAR:
                return ZOMBIE_FACTORY.gargantuar(pos, zombieType);
            default:
                throw new IllegalStateException("ERROR: zombie type has not been identified");
        }
    }

}

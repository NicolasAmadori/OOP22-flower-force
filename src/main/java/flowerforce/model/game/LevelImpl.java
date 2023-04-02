package flowerforce.model.game;

import flowerforce.model.entities.*;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.function.Function;

/**
 * This is an implementation of {@link Level}.
 */
public final class LevelImpl {

    private static final List<Function<Point2D, Zombie>> AVAILABLE_ZOMBIES = List.of(
            (pos) -> new ZombieFactoryImpl().basic(pos),
            (pos) -> new ZombieFactoryImpl().conehead(pos),
            (pos) -> new ZombieFactoryImpl().runner(pos),
            (pos) -> new ZombieFactoryImpl().newspaper(pos),
            (pos) -> new ZombieFactoryImpl().buckethead(pos),
            (pos) -> new ZombieFactoryImpl().quarterback(pos)
    );
    private static final List<Function<Point2D, Plant>> AVAILABLE_PLANTS = List.of(
            (pos) -> new SunflowerFactoryImpl().createCommonSunflower(pos),
            (pos) -> new ShootingPlantFactoryImpl().peashooter(pos),
            (pos) -> new ShootingPlantFactoryImpl().snow(pos),
            Wallnut::new,
            (pos) -> new ShootingPlantFactoryImpl().fire(pos),
            (pos) -> new ShootingPlantFactoryImpl().fast(pos)
    );
    private static final int COINS = 100;
    private static final List<Integer> ZOMBIE_LEVEL = List.of(20,40,60,60,60,80,80);
    private static final Function<Point2D, Zombie> ZOMBIE_BOSS = (pos) -> new ZombieFactoryImpl().gargantuar(pos);
    private static final int INFINITE_LEVEL_ID = 0;

    private LevelImpl() {}

    /**
     * {@inheritDoc}.
     */
    public static int getLevelCoins(final int id) {
        return COINS * id;
    }

    /**
     * {@inheritDoc}.
     */
    public static List<Function<Point2D, Plant>> getPlantsId(final int id) {
        if (INFINITE_LEVEL_ID == id) {
            return AVAILABLE_PLANTS;
        }
        return AVAILABLE_PLANTS.subList(0, Math.min(1 + id, AVAILABLE_ZOMBIES.size()));
    }

    /**
     * {@inheritDoc}.
     */
    public static List<Function<Point2D, Zombie>> getZombiesId(final int id) {
        if (INFINITE_LEVEL_ID == id) {
            return AVAILABLE_ZOMBIES;
        }
        return AVAILABLE_ZOMBIES.subList(0, Math.min(1 + id, AVAILABLE_ZOMBIES.size()));
    }

    /**
     * {@inheritDoc}.
     */
    public static Integer getTotalZombies(final int id) {
        return ZOMBIE_LEVEL.get(id-1);
    }

    /**
     * {@inheritDoc}.
     */
    public static Function<Point2D, Zombie> getBossId(final int id) {
        return 1 + id > AVAILABLE_ZOMBIES.size() ? ZOMBIE_BOSS : null;
    }

    public static int getInfiniteLevelId() {
        return INFINITE_LEVEL_ID;
    }
}

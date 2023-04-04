package flowerforce.model.game;

import flowerforce.model.entities.*;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * This is an implementation of .
 */
public final class Level {

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
            (pos) -> new ShootingPlantFactoryImpl().createPeaShooter(pos),
            (pos) -> new ShootingPlantFactoryImpl().createSnowShooter(pos),
            Wallnut::new,
            (pos) -> new ShootingPlantFactoryImpl().createFireShooter(pos),
            (pos) -> new ShootingPlantFactoryImpl().createFastShooter(pos)
    );
    private static final int COINS = 100;
    private static final List<Integer> ZOMBIE_LEVEL = List.of(34,51,68,68,68,85,85);
    private static final Function<Point2D, Zombie> ZOMBIE_BOSS = (pos) -> new ZombieFactoryImpl().gargantuar(pos);

    private Level() {}

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
        return AVAILABLE_PLANTS.subList(0, Math.min(1 + id, AVAILABLE_ZOMBIES.size()));
    }

    /**
     * {@inheritDoc}.
     */
    public static List<Function<Point2D, Zombie>> getZombiesId(final int id) {
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
    public static Optional<Function<Point2D, Zombie>> getBossId(final int id) {
        return 1 + id > AVAILABLE_ZOMBIES.size() ? Optional.of(ZOMBIE_BOSS) : Optional.empty();
    }
}

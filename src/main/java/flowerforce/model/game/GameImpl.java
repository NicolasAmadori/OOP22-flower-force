package flowerforce.model.game;

import flowerforce.common.TimerImpl;
import flowerforce.model.entities.*;
import javafx.geometry.Point2D;

import java.util.*;
import java.util.stream.Collectors;


/**
 * This is an implementation of {@link Game}.
 */
public class GameImpl implements Game {
    private static final int TIME_TO_SPAWN_SUN = 50;
    private static final int SUN_VALUE = 25;
    private static final int INITIAL_SUN = 2;
    private Set<Plant> plants = new HashSet<>();
    private Set<Zombie> zombies = new HashSet<>();
    private Set<Bullet> bullets = new HashSet<>();
    private TimerImpl zombieTimer;
    private final TimerImpl sunTimer;
    private final Map<IdConverter.Plants, TimerImpl> plantsTimer = new HashMap<>();
    private final Level level;
    private int sun;
    private int remainingZombie;
    private final int cellsSizeHeight;
    private final int cellsSizeWidth;
    private final int totalCols;


    /**
     * @param level level of the game that has started.
     * @param rows number of lines in the field.
     * @param cols number of columns in the field.
     * @param width field size corresponding to the width.
     * @param height field size corresponding to the height.
     */
    public GameImpl(final Level level, final int rows, final int cols, final int width, final int height) {
        sun = INITIAL_SUN * SUN_VALUE;
        this.level = level;
        zombieTimer = new TimerImpl(level.getTotalZombies());
        sunTimer = new TimerImpl(TIME_TO_SPAWN_SUN);
        remainingZombie = level.getTotalZombies();
        level.getPlantsId().forEach(p -> plantsTimer.put(p, new TimerImpl(p.getUnlockTime())));
        cellsSizeHeight = height / rows;
        cellsSizeWidth = width / cols;
        totalCols = cols;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.generateSun();
        this.generateZombie();
        bullets.forEach(Bullet::move);
        this.collidingBullet();
        this.collidingBullet();
        this.eatingPlant();
        this.updatePlant();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Zombie> getZombies() {
        return this.zombies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Plant> getPlants() {
        return this.plants;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Zombie> getZombieEating() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Bullet> getBullet() {
        return this.bullets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSun() {
        return this.sun;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placePlant(final IdConverter.Plants idPlant, final Point2D position) {
        for (final var plant : plants) {
            if (plant.getPosition().equals(position)) {
                return false;
            }
        }
        final var plant = IdConverter.createPlant(idPlant,
                new Point2D(position.getX() * cellsSizeHeight, position.getY() * cellsSizeWidth));
        sun -= idPlant.getCost();
        plants.add(plant);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        for (final var zombie : zombies) {
            if (zombie.getPosition().getY() == 0) {
                return true;
            }
        }
        return remainingZombie == 0 && zombies.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IdConverter.Plants> availablePlants() {
        final Set<IdConverter.Plants> availablePlant = new HashSet<>();
        for (final var plantType : level.getPlantsId()) {
            if (plantType.getCost() <= sun && plantsTimer.get(plantType).isReady()) {
                availablePlant.add(plantType);
            }
        }
        return availablePlant;
    }

    /**
     * decides whether to generate a sun.
     */
    private void generateSun() {
        if (sunTimer.isReady()) {
            sun += SUN_VALUE;
            sunTimer.reset();
        } else {
            sunTimer.updateState();
        }
    }

    /**
     * Checks which bullets hit the zombies and updates which zombies and bullets are still alive.
     */
    private void collidingBullet() {
         for (final var bullet : bullets) {
             for (final var zombie : zombies) {
                 if (bullet.getPosition().equals(zombie.getPosition())) {
                     bullet.hit(zombie);
                 }
             }
         }
         bullets = bullets.stream().filter(b -> !b.isOver()).collect(Collectors.toSet());
         zombies = zombies.stream().filter(z -> !z.isOver()).collect(Collectors.toSet());
    }

    /**
     * Check which zombies are eating and update which plants are still alive.
     */
    private void eatingPlant() {
        for (final var plant : plants) {
            for (final var zombie : zombies) {
                if (zombie.getPosition().equals(plant.getPosition())) {
                    zombie.manageEating(plant);
                } else {
                    zombie.move();
                }
            }
        }
        plants = plants.stream().filter(p -> !p.isOver()).collect(Collectors.toSet());
    }

    /**
     * Update the plants and check if they could produce suns or projectiles.
     */
    private void updatePlant() {
        for (final var plant : plants) {
            if (plant instanceof Sunflower) {
                if (((Sunflower) plant).isSunGenerated()) {
                    sun += SUN_VALUE;
                } else {
                    plant.updateState();
                }
            } else {
                final var bullet = ((ShootingPlant) plant).nextBullet();
                bullet.ifPresent(b -> bullets.add(b));
                plant.updateState();
            }
        }
    }

    /**
     *
     */
    private void generateZombie() {
        if (zombieTimer.isReady()) {
            final Random randomZombie = new Random();
            zombieTimer = new TimerImpl(remainingZombie);
            remainingZombie--;
            zombies.add(IdConverter.createZombie(IdConverter.Zombies.BASIC,
                    new Point2D(
                            randomZombie.nextInt(level.getNumberOfRowAvailable()) * cellsSizeHeight, cellsSizeWidth * totalCols)));
        }
        zombieTimer.updateState();
    }

}

package flowerforce.model.game;

import flowerforce.common.TimerImpl;
import flowerforce.model.entities.*;
import javafx.geometry.Point2D;

import java.util.*;


/**
 * Manages the development of the game.
 */
public class GameImpl implements Game {
    private static final int TIME_TO_SPAWN_SUN = 50;
    private static final int SUN_VALUE = 25;
    private static final int INITIAL_SUN = 2;
    private List<Plant> plants = new LinkedList<>();
    private List<Zombie> zombies = new LinkedList<>();
    private List<Bullet> bullets = new LinkedList<>();
    private TimerImpl zombieTimer;
    private final TimerImpl sunTimer;
    private final Map<IdConverter.Plants, TimerImpl> plantsTimer = new HashMap();
    private final Level level;
    private int sun;
    private int remainingZombie;

    /**
     * @param level level of the game that has started.
     */
    public GameImpl(final Level level) {
        sun = INITIAL_SUN * SUN_VALUE;
        this.level = level;
        zombieTimer = new TimerImpl(level.getTotalZombies());
        sunTimer = new TimerImpl(TIME_TO_SPAWN_SUN);
        remainingZombie = level.getTotalZombies();
        level.getPlantsId().forEach(p -> plantsTimer.put(p, new TimerImpl(p.getUnlockTime())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.generateSun();
        this.generateZombie();
        bullets.forEach(b -> b.move());
        this.collidingBullet();
        zombies.forEach(z -> z.move());
        this.collidingBullet();
        this.eatingPlant();
        this.updatePlant();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Zombie> getZombies() {
        return this.zombies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Plant> getPlants() {
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
    public List<Bullet> getBullet() {
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
        final var plant = IdConverter.createPlant(idPlant, position);
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
         bullets = bullets.stream().filter(b -> !b.isOver()).toList();
         zombies = zombies.stream().filter(z -> !z.isOver()).toList();
    }

    /**
     * Check which zombies are eating and update which plants are still alive.
     */
    private void eatingPlant() {
        for (final var plant : plants) {
            for (final var zombie : zombies) {
                if (zombie.getPosition().equals(plant.getPosition())) {
                    zombie.manageEating(plant);
                }
            }
        }
        plants = plants.stream().filter(p -> !p.isOver()).toList();
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
                    ((Sunflower) plant).updateState();
                }
            } else {
                final var bullet = ((ShootingPlant) plant).nextBullet();
                if (!bullet.isEmpty()) {
                    bullets.add(bullet.get());
                }
                ((ShootingPlant) plant).updateState();
            }
        }
    }

    /**
     *
     */
    private void generateZombie() {
        if (zombieTimer.isReady()) {
            //final Random randomZombie = new Random();
            zombieTimer = new TimerImpl(remainingZombie);
            remainingZombie--;
            zombies.add(IdConverter.createZombie(IdConverter.Zombies.BASIC, new Point2D(50, 50)));
        }
        zombieTimer.updateState();
    }

}

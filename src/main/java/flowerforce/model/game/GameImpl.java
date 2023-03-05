package flowerforce.model.game;

import flowerforce.common.TimerImpl;
import flowerforce.model.entities.*;
import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    }

    /**
     * {@inheritDoc}
     */
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
        return null;
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
        for (var plant : plants) {
            if (plant.getPosition().equals(position)) {
                return false;
            }
        }
        var plant = IdConverter.createPlant(idPlant, position);
        //sun = plant.ge
        plants.add(plant);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        for (var zombie : zombies) {
            if (zombie.getPosition().getY() == 0) {
                return true;
            }
        }
        return remainingZombie == 0 && zombies.size() == 0;
    }

    /**
     * decides whether to generate a sun.
     */
    private void generateSun() {
        if (sunTimer.isReady()) {
            sun += SUN_VALUE;
            sunTimer.reset();
        }
        else {
            sunTimer.updateState();
        }
    }

    /**
     * Checks which bullets hit the zombies and updates which zombies and bullets are still alive.
     */
    private void collidingBullet() {
         for (var bullet : bullets) {
             for (var zombie : zombies) {
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
        for (var plant : plants) {
            for (var zombie : zombies) {
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
        for (var plant : plants) {
            if (plant instanceof Sunflower) {
                if (((Sunflower) plant).isSunGenerated()) {
                    sun += SUN_VALUE;
                }
                else {
                    ((Sunflower) plant).updateState();
                }
            }
            else {
                var bullet = ((ShootingPlant) plant).nextBullet();
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
            Random randomZombie = new Random();
            zombieTimer = new TimerImpl(remainingZombie);
            remainingZombie--;
        }
        zombieTimer.updateState();
    }

}

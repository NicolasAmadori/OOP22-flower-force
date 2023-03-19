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
    private final Yard yard;

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
        this.yard = new YardImpl();
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
        this.eatingPlant();
        this.collidingBullet();
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
                yard.getRightEntityPosition((int) position.getX(), (int) position.getY()));
        sun -= idPlant.getCost();
        plants.add(plant);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final int nZombie = zombies.stream().filter(zombie -> zombie.getPosition().getX() <= 0)
                .collect(Collectors.toSet()).size();
        return (remainingZombie == 0 && zombies.isEmpty()) || nZombie > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IdConverter.Plants> availablePlants() {
        return level.getPlantsId().stream()
                .filter(plantType -> plantType.getCost() <= sun)
                .filter(plantType -> plantsTimer.get(plantType).isReady())
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean result() {
        return remainingZombie == 0 && zombies.isEmpty();
    }

    /**
     * decides whether to generate a sun.
     */
    private void generateSun() {
        sunTimer.updateState();
        if (sunTimer.isReady()) {
            sun += SUN_VALUE;
        }
    }

    /**
     * Checks which bullets hit the zombies and updates which zombies and bullets are still alive.
     */
    private void collidingBullet() {
         bullets.forEach(bullet -> zombies.stream()
                 .filter(zombie -> zombie.getPosition().getY() == bullet.getPosition().getY())
                 .filter(zombie -> zombie.getPosition().getX() >= bullet.getPosition().getX())
                 .filter(zombie -> zombie.getPosition().getX() <= bullet.getPosition().getX()
                         + bullet.getDeltaMovement())
                 .filter(zombie -> !zombie.isOver())
                 .min(Comparator.comparing(zombie -> zombie.getPosition().getX()))
                 .ifPresent(bullet::hit));

         zombies = zombies.stream().filter(z -> !z.isOver()).collect(Collectors.toSet());
         bullets = bullets.stream().filter(b -> !b.isOver()).collect(Collectors.toSet());
    }

    /**
     * Check which zombies are eating and update which plants are still alive.
     */
    private void eatingPlant() {
        Map<Zombie,Plant> zombieEating = new HashMap<>();
        plants.forEach(plant -> zombies.stream()
                .filter(zombie -> zombie.getPosition().getY() == plant.getPosition().getY())
                .filter(zombie -> zombie.getPosition().getX() <= plant.getPosition().getX())
                .filter(zombie -> zombie.getPosition().getX() > plant.getPosition().getX()
                        + yard.getCellDimension().getWidth())
                .forEach(zombie -> zombieEating.put(zombie,plant)));

        zombies.forEach(zombie -> {
                    if (zombieEating.containsKey(zombie)) {
                        zombie.manageEating(zombieEating.get(zombie));
                    } else {
                        zombie.move();
                    }
                });

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
                }
            } else {
                final var bullet = ((ShootingPlant) plant).nextBullet();
                bullet.ifPresent(b -> bullets.add(b));
            }
            plant.updateState();
        }
    }

    /**
     *
     */
    private void generateZombie() {
        //TODO :
        if (zombieTimer.isReady()) {
            final Random randomZombiePosition = new Random();
            zombieTimer = new TimerImpl(remainingZombie);
            remainingZombie--;
            zombies.add(IdConverter.createZombie(IdConverter.Zombies.BASIC,
                    yard.getRightEntityPosition(
                            randomZombiePosition.nextInt(yard.getRowsNum()),
                            yard.getColsNum()
                    )));
        }
        zombieTimer.updateState();
    }

}

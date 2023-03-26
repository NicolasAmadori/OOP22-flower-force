package flowerforce.model.game;

import flowerforce.model.entities.*;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

import java.util.*;
import java.util.stream.Collectors;


/**
 * This is an implementation of {@link Game}.
 */
public class GameImpl implements Game {
    private static final int TIME_TO_SPAWN_SUN = 500;
    private static final int SUN_VALUE = 25;
    private static final int INITIAL_SUN = 2;
    private Set<Plant> plants = new HashSet<>();
    private Set<Zombie> zombies = new HashSet<>();
    private Set<Bullet> bullets = new HashSet<>();
    private final TimerImpl sunTimer;
    private final Map<IdConverter.Plants, TimerImpl> plantsTimer = new HashMap<>();
    private final Level level;
    private int sun;
    private int remainingZombie;
    private final World world;
    private final ZombieGeneration generateZombie;

    /**
     * @param level level of the game that has started.
     * @param world the instance of the world starting the game.
     */
    public GameImpl(final Level level, final World world) {
        final ShootingPlantFactory factory = new ShootingPlantFactoryImpl();
        final ZombieFactory factoryZ = new ZombieFactoryImpl();
        this.sun = INITIAL_SUN * SUN_VALUE;
        this.level = level;
        this.sunTimer = new TimerImpl(TIME_TO_SPAWN_SUN);
        this.remainingZombie = level.getTotalZombies();
        this.level.getPlantsId().forEach(p -> plantsTimer.put(p, new TimerImpl(p.getUnlockTime())));
        this.world = world;
        this.generateZombie = new ZombieGenerationImpl(List.of(IdConverter.Zombies.BASIC,IdConverter.Zombies.BUCKETHEAD));
        this.plants.add(new SunflowerImpl(Yard.getEntityPosition(1,1),IdConverter.Plants.SUNFLOWER));
        this.plants.add(factory.common(Yard.getEntityPosition(2,0),IdConverter.Plants.PEASHOOTER));
        this.plants.add(factory.common(Yard.getEntityPosition(1,0),IdConverter.Plants.PEASHOOTER));
        this.plants.add(factory.common(Yard.getEntityPosition(0,0),IdConverter.Plants.PEASHOOTER));
        this.plants.add(factory.common(Yard.getEntityPosition(3,0),IdConverter.Plants.PEASHOOTER));
        this.plants.add(factory.common(Yard.getEntityPosition(4,0),IdConverter.Plants.PEASHOOTER));
        this.zombies.add(factoryZ.basic(Yard.getEntityPosition(2,8),IdConverter.Zombies.BASIC));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.generateSun();
        this.generateZombie();
        this.updateBullet();
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
    public Set<Plant> getPlacedPlants() {
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
    public boolean placePlant(final int idPlant, final int row, final int col) {
        final Point2D position = Yard.getEntityPosition(row, col);
        for (final var plant : this.plants) {
            if (plant.getPosition().equals(position)) {
                return false;
            }
        }
        final var plantType = IdConverter.Plants.values()[idPlant];
        final var plant = IdConverter.createPlant(plantType, position);
        this.plantsTimer.get(plantType).reset();
        this.plantsTimer.get(plantType).updateState();
        this.sun -= plantType.getCost();
        this.plants.add(plant);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final int nZombie = this.zombies.stream().filter(zombie -> zombie.getPosition().getX() <= 0)
                .collect(Collectors.toSet()).size();
        if (this.result()
                && this.world.getPlayer().getLastUnlockedLevelId() == this.level.getLevelId()) {
                this.world.getPlayer().unlockedNextLevel();
                this.world.getPlayer().addCoins(this.level.getLevelCoins());
        }
        return nZombie > 0 || this.result();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Integer> getAvailablePlantsIDs() {
        return this.level.getPlantsId().stream()
                .filter(plantType -> plantType.getCost() <= sun)
                .filter(plantType -> plantsTimer.get(plantType).isReady())
                .map(Enum::ordinal)
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean result() {
        return this.remainingZombie == 0 && this.zombies.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IdConverter.Plants> getAllPlantIDs() {
        return this.level.getPlantsId();
    }

    /**
     * decides whether to generate a sun.
     */
    private void generateSun() {
        this.sunTimer.updateState();
        if (this.sunTimer.isReady()) {
            this.sun += SUN_VALUE;
        }
    }

    /**
     * Check which bullets are still in the field
     */
    private void updateBullet() {
        this.bullets.forEach(Bullet::move);
        bullets = bullets.stream()
                .filter(bullet -> bullet.getPosition().getX() <
                        Yard.getCellDimension().getWidth() * Yard.getColsNum())
                .collect(Collectors.toSet());
    }

    /**
     * Checks which bullets hit the zombies and updates which zombies and bullets are still alive.
     */
    private void collidingBullet() {
         this.bullets.forEach(bullet -> zombies.stream()
                 .filter(zombie -> zombie.getPosition().getY() == bullet.getPosition().getY())
                 .filter(zombie -> zombie.getPosition().getX() <= bullet.getPosition().getX())
                 .filter(zombie -> zombie.getPosition().getX() >= bullet.getPosition().getX()
                         - bullet.getDeltaMovement() - zombie.getDeltaMovement())
                 .filter(zombie -> !zombie.isOver())
                 .min(Comparator.comparing(zombie -> zombie.getPosition().getX()))
                 .ifPresent(bullet::hit));
         this.zombies = this.zombies.stream().filter(z -> !z.isOver()).collect(Collectors.toSet());
         this.bullets = this.bullets.stream().filter(b -> !b.isOver()).collect(Collectors.toSet());
    }

    /**
     * Check which zombies are eating and update which plants are still alive.
     */
    private void eatingPlant() {
        final Map<Zombie, Plant> zombieEating = new HashMap<>();
        this.plants.forEach(plant -> this.zombies.stream()
                .filter(zombie -> zombie.getPosition().getY() == plant.getPosition().getY())
                .filter(zombie -> zombie.getPosition().getX() <= plant.getPosition().getX())
                .filter(zombie -> zombie.getPosition().getX() > plant.getPosition().getX()
                        - Yard.getCellDimension().getWidth())
                .forEach(zombie -> zombieEating.put(zombie, plant)));

        this.zombies.forEach(zombie -> {
                    if (zombieEating.containsKey(zombie)) {
                        zombie.manageEating(zombieEating.get(zombie));
                    } else {
                        zombie.move();
                    }
                });
        this.zombies.forEach(LivingEntity::updateState);
        this.plants = this.plants.stream().filter(p -> !p.isOver()).collect(Collectors.toSet());
    }

    /**
     * Update the plants and check if they could produce suns or projectiles.
     */
    private void updatePlant() {
        for (final var plant : plants) {
            plant.updateState();
            if (plant instanceof Sunflower) {
                if (((Sunflower) plant).isSunGenerated()) {
                    this.sun += SUN_VALUE;
                }
            } else {
                int nZombieOnRow = zombies.stream()
                        .filter(zombie -> plant.getPosition().getY() == zombie.getPosition().getY())
                        .filter(zombie -> plant.getPosition().getX() - Yard.getCellDimension().getWidth() <= zombie.getPosition().getX() )
                        .toList().size();
                if (nZombieOnRow > 0) {
                    final var bullet = ((ShootingPlant) plant).nextBullet();
                    bullet.ifPresent(b -> bullets.add(b));
                }
            }
        }
        plantsTimer.keySet().forEach(plantType -> {
            if (!plantsTimer.get(plantType).isReady()) {
                plantsTimer.get(plantType).updateState();
            }
        });
    }

    /**
     *
     */
    private void generateZombie() {
        var zombie = generateZombie.zombieGeneration();
        if (zombie.isPresent()) {
            remainingZombie--;
            zombies.add(zombie.get());
        }
    }

}

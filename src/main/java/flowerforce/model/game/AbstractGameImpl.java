package flowerforce.model.game;

import flowerforce.model.entities.*;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

import java.util.*;
import java.util.stream.Collectors;


/**
 * This is an implementation of {@link Game}.
 */
public abstract class AbstractGameImpl implements Game {
    private static final double STANDARD_SECS_SPAWN_SUN = 5.0;
    private static final int TIME_TO_SPAWN_SUN = (int) (STANDARD_SECS_SPAWN_SUN * RenderingInformation.getFramesPerSecond());
    private static final int SUN_VALUE = 25;
    private static final int INITIAL_SUN = 2;
    private Set<Plant> plants = new HashSet<>();
    private Set<Bullet> bullets = new HashSet<>();
    private final TimerImpl sunTimer;
    private final Map<IdConverter.Plants, TimerImpl> plantsTimer = new HashMap<>();
    private int sun;
    protected final ZombieGeneration generateZombie;
    protected final Level level;
    protected Set<Zombie> zombies = new HashSet<>();
    protected final World world;
    protected final int score;

    /**
     * @param level level of the game that has started.
     * @param world the instance of the world starting the game.
     */
    public AbstractGameImpl(final Level level, final World world) {
        this.sun = INITIAL_SUN * SUN_VALUE;
        this.level = level;
        this.sunTimer = new TimerImpl(TIME_TO_SPAWN_SUN);
        this.level.getPlantsId().forEach(p -> plantsTimer.put(p, new TimerImpl(p.getUnlockTime())));
        this.world = world;
        this.score = 0;
        this.generateZombie = new ZombieGenerationImpl(level);
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
        this.updatePlant();
        this.collidingBullet();
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
        final boolean zombieArrived = this.zombies.stream().filter(zombie -> zombie.getPosition().getX() <= 0)
                .collect(Collectors.toSet()).size() > 0 ;
        return zombieArrived || this.result();
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
    public List<IdConverter.Plants> getAllPlantIDs() {
        return this.level.getPlantsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removePlant(final int row, final int col) {
        final var positionPlant = Yard.getEntityPosition(row, col);
        for (final var plant : plants) {
            if (plant.getPosition().equals(positionPlant)) {
                plants.remove(plant);
                return true;
            }
        }
        return false;
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
     * Check which bullets are still in the field.
     */
    private void updateBullet() {
        this.bullets.forEach(Bullet::move);
        bullets = bullets.stream()
                .filter(bullet -> bullet.getPosition().getX()
                        < Yard.getCellDimension().getWidth() * Yard.getColsNum())
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
            } else if (plant instanceof ShootingPlant) {
                final int nZombieOnRow = zombies.stream()
                        .filter(zombie -> plant.getPosition().getY() == zombie.getPosition().getY())
                        .filter(zombie -> plant.getPosition().getX()
                                - Yard.getCellDimension().getWidth() / 2 <= zombie.getPosition().getX())
                        .toList().size();
                if (nZombieOnRow > 0) {
                    final var bullet = ((ShootingPlant) plant).nextBullet();
                    bullet.ifPresent(b -> bullets.add(b));
                }
            } else if (plant instanceof ExplodingPlant) {
                if (((ExplodingPlant) plant).hasExploded()) {
                    ((ExplodingPlant) plant).explodeOver(zombies.stream()
                            .filter(zombie -> zombie.getPosition().getY() == plant.getPosition().getY())
                            .filter(zombie -> zombie.getPosition().getX() - ((ExplodingPlant) plant).getRadius()
                            > plant.getPosition().getX())
                            .filter(zombie -> zombie.getPosition().getX() - ((ExplodingPlant) plant).getRadius()
                                    > plant.getPosition().getX())
                            .toList());
                }
            }
        }
        plantsTimer.keySet().forEach(plantType -> {
            if (!plantsTimer.get(plantType).isReady()) {
                plantsTimer.get(plantType).updateState();
            }
        });
    }

    protected abstract void generateZombie();

}

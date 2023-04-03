package flowerforce.model.game;

import flowerforce.model.entities.*;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is an implementation of {@link Game}.
 */
public abstract class AbstractGameImpl implements Game {
    private final Map<Pair<String,Integer>, Function<Point2D,Plant>> placeablePlant;
    private static final double STANDARD_SECS_SPAWN_SUN = 5.0;
    private static final int TIME_TO_SPAWN_SUN = (int) (STANDARD_SECS_SPAWN_SUN * RenderingInformation.getFramesPerSecond());
    private static final int SUN_VALUE = 25;
    private static final int INITIAL_SUN = 2;
    private Set<Plant> plants = new HashSet<>();
    private Set<Bullet> bullets = new HashSet<>();
    private Set<Zombie> zombies = new HashSet<>();
    private final TimerImpl sunTimer;
    private final Map<Pair<String,Integer>, TimerImpl> plantsTimer = new HashMap<>();
    private int sun;
    private final int levelId;
    private final World world;
    private int score;
    private static final Point2D TEMPORARY_POSITION = new Point2D(0,0);

    /**
     * Constructor to instantiate an infinite game.
     * @param id of the game started
     * @param world an instance of the world that started the game
     */
    public AbstractGameImpl(final int id, final World world) {
        var pos = new Point2D(0,0);
        this.placeablePlant = new HashMap<>();
        Level.getPlantsId(id).forEach(p -> placeablePlant.put(
                new Pair<>(p.apply(TEMPORARY_POSITION).getName(),
                        p.apply(TEMPORARY_POSITION).getCost()),p)
        );
        this.placeablePlant.keySet().forEach(p -> Level.getPlantsId(id));
        this.sun = INITIAL_SUN * SUN_VALUE;
        this.levelId = id;
        this.sunTimer = new TimerImpl(TIME_TO_SPAWN_SUN);
        this.placeablePlant.keySet().forEach(p -> plantsTimer
                .put(p, new TimerImpl(placeablePlant.get(p).apply(TEMPORARY_POSITION).getRechargeTime())));
        this.world = world;
        this.score = 0;
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
     * @param zombie to add to the list of spawned zombie
     */
    protected void addZombie(final Zombie zombie) {
        this.zombies.add(zombie);
    }

    /**
     * @return the actual value of the game score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @return the instance of the World
     */
    protected World getWorld() {
        return this.world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Pair<String,Point2D>> getPlacedZombies() {
        return zombies.stream().map(z -> new Pair<>(z.getName(),z.getPosition())).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Pair<String,Point2D>> getPlacedPlants() {
        return plants.stream().map(p -> new Pair<>(p.getName(),p.getPosition())).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Pair<String,Point2D>> getPlacedBullet() {
        return bullets.stream().map(b -> new Pair<>(b.getName(),b.getPosition())).collect(Collectors.toSet());
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
    public boolean placePlant(final Pair<String,Integer> plantInfo, final int row, final int col) {
        final Point2D position = Yard.getEntityPosition(row, col);
        for (final var plant : this.plants) {
            if (plant.getPosition().equals(position)) {
                return false;
            }
        }
        final var plant = placeablePlant.get(plantInfo).apply(position);
        plants.add(plant);
        this.plantsTimer.get(plantInfo).reset();
        this.plantsTimer.get(plantInfo).updateState();
        this.sun -= plantInfo.getValue();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final boolean zombieArrived = this.zombies.stream().filter(zombie -> zombie.getPosition().getX() <= 0)
                .collect(Collectors.toSet()).isEmpty();
        return !zombieArrived || this.result();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Pair<String,Integer>> getEnabledPlants() {
        return this.placeablePlant.keySet().stream()
                .filter(plantType -> plantType.getValue() <= sun)
                .filter(plantType -> plantsTimer.get(plantType).isReady())
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String,Integer>> getAllPlant() {
        return this.placeablePlant.keySet().stream().toList();
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
     * Decides whether to generate a sun.
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
         this.zombies.stream().filter(Entity::isOver).forEach(z -> score += z.getDifficulty());
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
     * Update the plants and check what they can do with the actual state.
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
            } else if (plant instanceof ExplodingPlant && ((ExplodingPlant) plant).hasExploded()) {
                ((ExplodingPlant) plant).explodeOver(zombies.stream()
                        .filter(zombie -> zombie.getPosition().getY() == plant.getPosition().getY())
                        .filter(zombie -> zombie.getPosition().getX() - ((ExplodingPlant) plant).getRadius()
                        > plant.getPosition().getX())
                        .filter(zombie -> zombie.getPosition().getX() - ((ExplodingPlant) plant).getRadius()
                                > plant.getPosition().getX())
                        .toList());
            }
        }
        plantsTimer.keySet().forEach(plantType -> {
            if (!plantsTimer.get(plantType).isReady()) {
                plantsTimer.get(plantType).updateState();
            }
        });
    }

    /**
     * Used for the generation zombie management.
     */
    protected abstract void generateZombie();

}

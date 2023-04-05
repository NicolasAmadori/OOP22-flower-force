package flowerforce.model.game;


import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.PlantInfo;
import flowerforce.model.entities.Zombie;
import flowerforce.model.entities.EntityInfo;
import flowerforce.model.entities.PlantInfoImpl;
import flowerforce.model.entities.Entity;
import flowerforce.model.entities.ExplodingPlant;
import flowerforce.model.entities.ShootingPlant;
import flowerforce.model.entities.Sunflower;
import flowerforce.model.entities.LivingEntity;


import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is an implementation of {@link Game}.
 */
public abstract class AbstractGameImpl implements Game {
    private final Map<PlantInfo, Function<Point2D, Plant>> placeablePlant;
    private static final double STANDARD_SECS_SPAWN_SUN = 5.0;
    private static final int TIME_TO_SPAWN_SUN = (int)
            (STANDARD_SECS_SPAWN_SUN * RenderingInformation.getFramesPerSecond());
    private static final int SUN_VALUE = 25;
    private static final int INITIAL_SUN = 2;
    private Set<Plant> plants = new HashSet<>();
    private Set<Bullet> bullets = new HashSet<>();
    private Set<Zombie> zombies = new HashSet<>();
    private final Set<EntityInfo> damagedEntities = new HashSet<>();
    private final TimerImpl sunTimer;
    private final Map<PlantInfo, TimerImpl> plantsTimer = new HashMap<>();
    private int sun;
    private final World world;
    private int score;
    private static final Point2D TEMPORARY_POSITION = new Point2D(0, 0);
    private final Map<Zombie, Plant> zombieEating = new HashMap<>();
    /**
     * Constructor to instantiate an infinite game.
     * @param id of the game started
     * @param world an instance of the world that started the game
     */
    public AbstractGameImpl(final int id, final World world) {
        this.placeablePlant = new HashMap<>();
        LevelInfo.getPlantsInfo(id).forEach(p -> placeablePlant.put(
                new PlantInfoImpl(p.apply(TEMPORARY_POSITION).getName(),
                        p.apply(TEMPORARY_POSITION).getCost()), p)
        );
        world.getShop().getBoughtPlantsFunctions().forEach(p -> placeablePlant.put(
                new PlantInfoImpl(p.apply(TEMPORARY_POSITION).getName(),
                        p.apply(TEMPORARY_POSITION).getCost()), p)
        );
        this.sun = INITIAL_SUN * SUN_VALUE;
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
     * {@inheritDoc}
     */
    @Override
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
    public Set<EntityInfo> getPlacedZombies() {
        return zombies.stream().map(Entity::getEntityInfo).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityInfo> getDamagedEntity() {
        final var tmpDamagedEntities = Set.copyOf(this.damagedEntities);
        damagedEntities.clear();
        return tmpDamagedEntities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityInfo> getPlacedPlants() {
        return plants.stream().map(Entity::getEntityInfo).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityInfo> getPlacedBullet() {
        return bullets.stream().map(Entity::getEntityInfo).collect(Collectors.toSet());
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
    public boolean placePlant(final PlantInfo plantInfo, final int row, final int col) {
        final Point2D position = YardInfo.getEntityPosition(row, col);
        for (final var plant : this.plants) {
            if (plant.getPosition().equals(position)) {
                return false;
            }
        }
        final var plant = placeablePlant.get(plantInfo).apply(position);
        plants.add(plant);
        this.plantsTimer.get(plantInfo).reset();
        this.plantsTimer.get(plantInfo).updateState();
        this.sun -= plantInfo.getCost();
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
    public Set<PlantInfo> getEnabledPlants() {
        return this.placeablePlant.keySet().stream()
                .filter(plantType -> plantType.getCost() <= sun)
                .filter(plantType -> plantsTimer.get(plantType).isReady())
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PlantInfo> getPlaceablePlant() {
        return this.placeablePlant.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removePlant(final int row, final int col) {
        final var positionPlant = YardInfo.getEntityPosition(row, col);
        for (final var plant : plants) {
            if (plant.getPosition().equals(positionPlant)) {
                plants.remove(plant);
                return true;
            }
        }
        return false;
    }

    /*
     * Decides whether to generate a sun.
     */
    private void generateSun() {
        this.sunTimer.updateState();
        if (this.sunTimer.isReady()) {
            this.sun += SUN_VALUE;
        }
    }

    /*
     * Check which bullets are still in the field.
     */
    private void updateBullet() {
        this.bullets.forEach(Bullet::move);
        bullets = bullets.stream()
                .filter(bullet -> bullet.getPosition().getX()
                        < YardInfo.getCellDimension().getWidth() * YardInfo.getColsNum())
                .collect(Collectors.toSet());
    }

    /*
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
                 .ifPresent(z -> {
                     bullet.hit(z);
                     damagedEntities.add(z.getEntityInfo());
                 }));
         this.zombies.stream().filter(Entity::isOver).forEach(z -> score += z.getDifficulty() * 100);
         this.zombies = this.zombies.stream().filter(z -> !z.isOver()).collect(Collectors.toSet());
         this.bullets = this.bullets.stream().filter(b -> !b.isOver()).collect(Collectors.toSet());
    }

    /*
     * Check which zombies are eating and update which plants are still alive.
     */
    private void eatingPlant() {
        this.plants.forEach(plant -> this.zombies.stream()
                .filter(zombie -> zombie.getPosition().getY() == plant.getPosition().getY())
                .filter(zombie -> zombie.getPosition().getX() <= plant.getPosition().getX())
                .filter(zombie -> zombie.getPosition().getX() > plant.getPosition().getX()
                        - YardInfo.getCellDimension().getWidth())
                .forEach(zombie -> {
                        zombieEating.put(zombie, plant);
                    }
                ));

        this.zombies.forEach(zombie -> {
            if (zombieEating.containsKey(zombie)) {
                if (zombie.manageEating(zombieEating.get(zombie))) {
                    this.damagedEntities.add(zombieEating.get(zombie).getEntityInfo());
                }
            } else {
                zombie.move();
            }
        });
        this.zombies.forEach(LivingEntity::updateState);
        this.plants = this.plants.stream().filter(p -> !p.isOver()).collect(Collectors.toSet());
    }

    /*
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
                        .filter(zombie -> plant.getPosition().getX() <= zombie.getPosition().getX())
                        .toList().size();
                if (nZombieOnRow > 0 && !zombieEating.containsValue(plant)) {
                    final var bullet = ((ShootingPlant) plant).nextBullet();
                    bullet.ifPresent(b -> bullets.add(b));
                }
            } else if (plant instanceof ExplodingPlant && ((ExplodingPlant) plant).hasExploded()) {
                final var bottomRightCorner = YardInfo.toBottomRightCorner(plant.getPosition(), ((ExplodingPlant) plant).getRadius());
                final var topLeftCorner = YardInfo.toTopLeftCorner(plant.getPosition(), ((ExplodingPlant) plant).getRadius());

                ((ExplodingPlant) plant).explodeOver(zombies.stream()
                        .filter(zombie -> zombie.getPosition().getY() <= bottomRightCorner.getY())
                        .filter(zombie -> zombie.getPosition().getY() >= topLeftCorner.getY())
                        .filter(zombie -> zombie.getPosition().getX() <= bottomRightCorner.getX())
                        .filter(zombie -> zombie.getPosition().getX() >= topLeftCorner.getX())
                        .collect(Collectors.toSet()));
            }
        }
        zombieEating.clear();
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

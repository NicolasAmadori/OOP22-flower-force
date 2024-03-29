package flowerforce.model.game;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import flowerforce.model.entities.bullets.Bullet;
import flowerforce.model.entities.plants.ExplodingPlant;
import flowerforce.model.entities.plants.Plant;
import flowerforce.model.entities.plants.PlantInfo;
import flowerforce.model.entities.plants.PlantInfoImpl;
import flowerforce.model.entities.plants.ShootingPlant;
import flowerforce.model.entities.plants.Sunflower;
import flowerforce.model.entities.zombies.Zombie;
import flowerforce.model.entities.EntityInfo;
import flowerforce.model.entities.Entity;
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
public abstract class AbstractGame implements Game {
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
    private final Player player;
    private int score;
    private static final Point2D TEMPORARY_POSITION = new Point2D(0, 0);
    private final Map<Zombie, Plant> zombieEating = new HashMap<>();

    /**
     * Constructor to instantiate a game.
     * @param id of the game started
     * @param shop an instance of the shop
     * @param player an instance of the player
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Cannot copy player because the instance must remain the same"
    )
    public AbstractGame(final int id, final Shop shop, final Player player) {
        this.placeablePlant = new HashMap<>();
        LevelInfo.getPlantsInfo(id).forEach(p -> placeablePlant.put(
                new PlantInfoImpl(p.apply(TEMPORARY_POSITION).getName(),
                        p.apply(TEMPORARY_POSITION).getCost()), p)
        );
        shop.getBoughtPlantsFunctions().forEach(p -> placeablePlant.put(
                new PlantInfoImpl(p.apply(TEMPORARY_POSITION).getName(),
                        p.apply(TEMPORARY_POSITION).getCost()), p)
        );
        this.sun = INITIAL_SUN * SUN_VALUE;
        this.player = player;
        this.sunTimer = new TimerImpl(TIME_TO_SPAWN_SUN);
        this.placeablePlant.keySet().forEach(p -> this.plantsTimer
                .put(p, new TimerImpl(this.placeablePlant.get(p).apply(TEMPORARY_POSITION).getRechargeTime())));
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
     * @return the instance of the Player
     */
    protected Player getPlayer() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityInfo> getPlacedZombies() {
        return this.zombies.stream().map(Entity::getEntityInfo).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityInfo> getDamagedEntity() {
        final var tmpDamagedEntities = Set.copyOf(this.damagedEntities);
        this.damagedEntities.clear();
        return tmpDamagedEntities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityInfo> getPlacedPlants() {
        return this.plants.stream().map(Entity::getEntityInfo).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityInfo> getPlacedBullet() {
        return this.bullets.stream().map(Entity::getEntityInfo).collect(Collectors.toSet());
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
        final var plant = this.placeablePlant.get(plantInfo).apply(position);
        this.plants.add(plant);
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
                .filter(plantType -> plantType.getCost() <= this.sun)
                .filter(plantType -> this.plantsTimer.get(plantType).isReady())
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
        for (final var plant : this.plants) {
            if (plant.getPosition().equals(positionPlant)) {
                this.plants.remove(plant);
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
     * Moves bullets and removes those that are no longer in the field.
     */
    private void updateBullet() {
        this.bullets.forEach(Bullet::move);
        this.bullets = this.bullets.stream()
                .filter(bullet -> bullet.getPosition().getX()
                        < YardInfo.getCellDimension().getWidth() * YardInfo.getColsNum())
                .collect(Collectors.toSet());
    }

    /*
     * Check and remove bullets colliding with zombies and remove dead zombies.
     */
    private void collidingBullet() {
         this.bullets.forEach(bullet -> this.zombies.stream()
                 .filter(zombie -> zombie.getPosition().getY() == bullet.getPosition().getY())
                 .filter(zombie -> zombie.getPosition().getX() <= bullet.getPosition().getX())
                 .filter(zombie -> zombie.getPosition().getX() >= bullet.getPosition().getX()
                         - bullet.getDeltaMovement() - zombie.getDeltaMovement())
                 .filter(zombie -> !zombie.isOver())
                 .min(Comparator.comparing(zombie -> zombie.getPosition().getX()))
                 .ifPresent(z -> {
                     bullet.hit(z);
                     this.damagedEntities.add(z.getEntityInfo());
                 }));
         this.zombies.stream().filter(Entity::isOver).forEach(z -> this.score += z.getDifficulty() * 100);
         this.zombies = this.zombies.stream().filter(z -> !z.isOver()).collect(Collectors.toSet());
         this.bullets = this.bullets.stream().filter(b -> !b.isOver()).collect(Collectors.toSet());
    }

    /*
     * It hits plants that are being eaten by zombies and moves zombies that aren't eating.
     */
    private void eatingPlant() {
        this.plants.forEach(plant -> this.zombies.stream()
                .filter(zombie -> zombie.getPosition().getY() == plant.getPosition().getY())
                .filter(zombie -> zombie.getPosition().getX() <= plant.getPosition().getX())
                .filter(zombie -> zombie.getPosition().getX() > plant.getPosition().getX()
                        - YardInfo.getCellDimension().getWidth())
                .forEach(zombie -> {
                        this.zombieEating.put(zombie, plant);
                    }
                ));

        this.zombies.forEach(zombie -> {
            if (this.zombieEating.containsKey(zombie)) {
                if (zombie.manageEating(this.zombieEating.get(zombie))) {
                    this.damagedEntities.add(this.zombieEating.get(zombie).getEntityInfo());
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
        for (final var plant : this.plants) {
            plant.updateState();
            if (plant instanceof Sunflower) {
                if (((Sunflower) plant).isSunGenerated()) {
                    this.sun += SUN_VALUE;
                }
            } else if (plant instanceof ShootingPlant) {
                final int nZombieOnRow = this.zombies.stream()
                        .filter(zombie -> plant.getPosition().getY() == zombie.getPosition().getY())
                        .filter(zombie -> plant.getPosition().getX() <= zombie.getPosition().getX())
                        .toList().size();
                if (nZombieOnRow > 0 && !this.zombieEating.containsValue(plant)) {
                    final var bullet = ((ShootingPlant) plant).nextBullet();
                    bullet.ifPresent(b -> bullets.add(b));
                }
            } else if (plant instanceof ExplodingPlant && ((ExplodingPlant) plant).hasExploded()) {
                final var bottomRightCorner = YardInfo.toBottomRightCorner(plant.getPosition(),
                        ((ExplodingPlant) plant).getRadius());
                final var topLeftCorner = YardInfo.toTopLeftCorner(plant.getPosition(), ((ExplodingPlant) plant).getRadius());

                ((ExplodingPlant) plant).explodeOver(this.zombies.stream()
                        .filter(zombie -> zombie.getPosition().getY() <= bottomRightCorner.getY())
                        .filter(zombie -> zombie.getPosition().getY() >= topLeftCorner.getY())
                        .filter(zombie -> zombie.getPosition().getX() <= bottomRightCorner.getX())
                        .filter(zombie -> zombie.getPosition().getX() >= topLeftCorner.getX())
                        .collect(Collectors.toSet()));
            }
        }
        this.zombieEating.clear();
        this.plantsTimer.keySet().forEach(plantType -> {
            if (!this.plantsTimer.get(plantType).isReady()) {
                this.plantsTimer.get(plantType).updateState();
            }
        });
    }

    /**
     * Used for the generation zombie management.
     */
    protected abstract void generateZombie();

}

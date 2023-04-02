package flowerforce.controller;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.NoSuchElementException;
import flowerforce.controller.utilities.WorldSavingManager;
import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import flowerforce.model.game.Game;
import flowerforce.model.game.World;
import flowerforce.controller.utilities.EntityConverter;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.EntityView;
import flowerforce.view.game.GameEngine;

/**
 * This is an implementation of {@link Controller}.
 */
public final class ControllerImpl implements Controller {

    private Optional<GameEngine> gameEngine = Optional.empty();
    private final World world;

    private EntityConverter entityConverter;
    private Optional<Game> game;

    private final Map<Plant, EntityView> previousPlant = new HashMap<>();

    private final Map<Zombie, EntityView> previousZombie = new HashMap<>();

    private final Map<Bullet, EntityView> previousBullet = new HashMap<>();

    /**
     * Create a new instance of Controller.
     */
    public ControllerImpl() throws InstantiationException {
        this.world = WorldSavingManager.load();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCoins() {
        return this.world.getPlayer().getCoins();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerScoreRecord() {
        return this.world.getPlayer().getScoreRecord();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastUnlockedLevelId() {
        return this.world.getPlayer().getLastUnlockedLevelId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFramesPerSecond() {
        return this.world.getRenderingInformations();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = Optional.ofNullable(gameEngine);
        checkGameEngine();
        this.entityConverter = new EntityConverter(this.world.getYardDimension(), this.gameEngine.get().getYardDimension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameEngine getGameEngine() {
        checkGameEngine();
        return this.gameEngine.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSunCounter() {
        checkGame();
        return this.game.get().getSun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placePlant(final int plantId, final int row, final int col) {
        checkGame();
        return this.game.get().placePlant(plantId, row, col);
    }

    @Override
    public boolean removePlant(final int row, final int col) {
        checkGame();
        return this.game.get().removePlant(row, col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game startNewLevelGame(final int levelId) {
        this.game = Optional.of(this.world.createLevelGame(levelId));
        checkGame();
        checkGameEngine();
        this.gameEngine.get().loadCards(this.getCards());
        return this.game.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game startNewInfiniteGame() {
        this.game = Optional.of(this.world.createInfiniteGame());
        checkGame();
        checkGameEngine();
        this.gameEngine.get().loadCards(this.getCards());
        return this.game.get();
    }

    @Override
    public Set<EntityView> getPlacedEntities() {
        checkGame();
        final Set<Plant> plants = this.game.get().getPlacedPlants();
        final Set<Zombie> zombies = this.game.get().getZombies();
        final Set<Bullet> bullets = this.game.get().getBullet();

        //TODO: refactor
        //region Plants
        final Set<Plant> plantsToRemove = new HashSet<>();
        //Remove the entities that are no longer there
        this.previousPlant.keySet().forEach(p -> {
            if (!plants.contains(p)) {
                plantsToRemove.add(p);
            }
        });
        plantsToRemove.forEach(p -> this.previousPlant.remove(p));
        //Create the plant EntityView if plant not already present
        plants.forEach(p -> {
            if (!this.previousPlant.containsKey(p)) {
                this.previousPlant.put(p, this.entityConverter.getEntityView(p));
            }
        });
        //endregion

        //region Zombies
        final Set<Zombie> zombiesToRemove = new HashSet<>();
        this.previousZombie.keySet().forEach(z -> {
            if (!zombies.contains(z)) {
                zombiesToRemove.add(z);
            }
        });
        zombiesToRemove.forEach(z -> this.previousZombie.remove(z));
        zombies.forEach(z -> {
            if (this.previousZombie.containsKey(z)) {
                this.entityConverter.changeZombieViewPosition(this.previousZombie.get(z), z.getPosition());
            } else {
                this.previousZombie.put(z, this.entityConverter.getEntityView(z));
            }
        });
        //endregion

        //region Bullets
        final Set<Bullet> bulletToRemove = new HashSet<>();
        this.previousBullet.keySet().forEach(b -> {
            if (!bullets.contains(b)) {
                bulletToRemove.add(b);
            }
        });
        bulletToRemove.forEach(b -> this.previousBullet.remove(b));
        bullets.forEach(b -> {
            if (this.previousBullet.containsKey(b)) {
                this.entityConverter.changeBulletViewPosition(this.previousBullet.get(b), b.getPosition());
            } else {
                this.previousBullet.put(b, this.entityConverter.getEntityView(b));
            }
        });
        //endregion

        final Set<EntityView> output = new HashSet<>();
        output.addAll(this.previousPlant.values());
        output.addAll(this.previousZombie.values());
        output.addAll(this.previousBullet.values());

        return output;
    }


    private List<CardView> getCards() {
        checkGame();
        final List<IdConverter.Plants> plants = this.game.get().getAllPlantIDs();
        final List<CardView> cards = new ArrayList<>();
        plants.forEach(p -> cards.add(entityConverter.getCardView(p)));
        return cards;
    }

    @Override
    public Set<Integer> getEnabledCards() {
        checkGame();
        return this.game.get().getAvailablePlantsIDs(); //uncomment this when game is corrected
    }

    @Override
    public int getTotalRows() {
        return this.world.getRowsNum();
    }

    @Override
    public int getTotalColumns() {
        return this.world.getColsNum();
    }

    @Override
    public void save() {
        WorldSavingManager.save(this.world);
    }

    private void checkGameEngine() {
        if (this.gameEngine.isEmpty()) {
            throw new NoSuchElementException("GameEngine has not been set.");
        }
    }

    private void checkGame() {
        if (this.game.isEmpty()) {
            throw new NoSuchElementException("Game has not been started.");
        }
    }

    @Override
    public Set<EntityView> getDamagedEntities() {
        int nZombies = this.previousZombie.size();
        Random r = new Random();
        int nZombiesToDamage = r.nextInt(nZombies);
        Set<EntityView> output = new HashSet<>();
        this.previousZombie.forEach((p,e) -> {
            if (output.size() < nZombiesToDamage) {
                output.add(e);
            }
        });
        return output;
    }
}
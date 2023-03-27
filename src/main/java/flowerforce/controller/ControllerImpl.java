package flowerforce.controller;

import flowerforce.controller.utilities.WorldSavingManager;
import flowerforce.model.entities.*;
import flowerforce.model.game.Game;
import flowerforce.model.game.World;
import flowerforce.view.entities.CardView;
import flowerforce.model.utilities.EntityConverter;
import flowerforce.view.entities.EntityView;
import flowerforce.view.game.GameEngine;

import java.util.*;

/**
 * This is an implementation of {@link Controller}.
 */
public final class ControllerImpl implements Controller {

    private Optional<GameEngine> gameEngine = Optional.empty();
    private final World world;

    private EntityConverter entityConverter;
    private Optional<Game> game;

    private Map<Plant, EntityView> previousPlant = new HashMap<>();

    private Map<Zombie, EntityView> previousZombie = new HashMap<>();

    private Map<Bullet, EntityView> previousBullet = new HashMap<>();

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
        this.entityConverter = new EntityConverter(this.world.getYardDimension(), this.gameEngine.get().getYardDimension(), this.gameEngine.get().getImageResizeFactor());
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
    public boolean removePlant(int row, int col) {
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


        //region Plants
        final Set<Plant> plantsToRemove = new HashSet<>();
        //Remove the entities that are no longer there
        this.previousPlant.keySet().forEach(p -> {
            if(!plants.contains(p)) {
                plantsToRemove.add(p);
            }
        });
        plantsToRemove.forEach(p -> this.previousPlant.remove(p));
        //Update the position of the EntityView if presents, otherwise EntityView is created
        plants.forEach(p -> {
            if(this.previousPlant.containsKey(p)) {
                this.entityConverter.changePlantViewPosition(this.previousPlant.get(p), p.getPosition());
            }
            else {
                this.previousPlant.put(p, this.entityConverter.getEntityView(p));
            }
        });
        //endregion

        //region Zombies
        final Set<Zombie> zombiesToRemove = new HashSet<>();
        this.previousZombie.keySet().forEach(z -> {
            if(!zombies.contains(z)) {
                zombiesToRemove.add(z);
            }
        });
        zombiesToRemove.forEach(z -> this.previousZombie.remove(z));
        zombies.forEach(z -> {
            if(this.previousZombie.containsKey(z)) {
                this.entityConverter.changeZombieViewPosition(this.previousZombie.get(z), z.getPosition());
            }
            else {
                this.previousZombie.put(z, this.entityConverter.getEntityView(z));
            }
        });
        //endregion

        //region Bullets
        final Set<Bullet> bulletToRemove = new HashSet<>();
        this.previousBullet.keySet().forEach(b -> {
            if(!bullets.contains(b)) {
                bulletToRemove.add(b);
            }
        });
        bulletToRemove.forEach(b -> this.previousBullet.remove(b));
        bullets.forEach(b -> {
            if(this.previousBullet.containsKey(b)) {
                this.entityConverter.changeBulletViewPosition(this.previousBullet.get(b), b.getPosition());
            }
            else {
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
        return this.game.get().getAvailablePlantsIDs();//uncomment this when game is corrected
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
}

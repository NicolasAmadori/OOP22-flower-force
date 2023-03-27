package flowerforce.controller;

import flowerforce.controller.utilities.WorldSavingManager;
import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
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
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = Optional.ofNullable(gameEngine);
        checkGameEngine();
        this.entityConverter = new EntityConverter(this.world.getYardDimension(), this.gameEngine.get().getYardSize(), this.gameEngine.get().getImageResizeFactor());
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

        final Set<Plant> plantsToRemove = new HashSet<>();
        this.previousPlant.keySet().forEach(p -> {
            if(!plants.contains(p)) {
                plantsToRemove.add(p);
            }
        });
        plantsToRemove.forEach(p -> this.previousPlant.remove(p));
        plants.forEach(p -> {
            if(this.previousPlant.containsKey(p)) {
                this.previousPlant.get(p).setPosition(p.getPosition());//TODO: wait for implementationg
            }
            else {
                this.previousPlant.put(p, this.entityConverter.getEntityView(p));
            }
        });
        
        final Set<EntityView> output = new HashSet<>();
        this.previousPlant.forEach(p -> output.add(entityConverter.getEntityView(p)));
        zombies.forEach(z -> output.add(entityConverter.getEntityView(z)));
        bullets.forEach(z -> output.add(entityConverter.getEntityView(z)));

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

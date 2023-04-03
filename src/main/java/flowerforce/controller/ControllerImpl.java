package flowerforce.controller;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import flowerforce.controller.utilities.CardGenerator;
import flowerforce.controller.utilities.EntityConverter;
import flowerforce.controller.utilities.WorldSavingManager;
import flowerforce.model.game.Game;
import flowerforce.model.game.World;
import flowerforce.controller.utilities.EntityConverterImpl;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.EntityView;
import flowerforce.view.game.GameEngine;
import javafx.util.Pair;
import javafx.geometry.Point2D;

/**
 * This is an implementation of {@link Controller}.
 */
public final class ControllerImpl implements Controller {

    private Optional<GameEngine> gameEngine = Optional.empty();
    private final World world;
    private EntityConverter entityConverter;
    private Optional<Game> game;

    private Map<CardView, Pair<String, Integer>> cards = new HashMap<>();
    private Map<Pair<String, Point2D>, EntityView> previousPlant = new HashMap<>();
    private Map<Pair<String, Point2D>, EntityView> previousZombie = new HashMap<>();
    private Map<Pair<String, Point2D>, EntityView> previousBullet = new HashMap<>();
    private Map<CardView, Pair<String,Integer>> purchasablePlants = new HashMap<>();

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
        this.entityConverter = new EntityConverterImpl(this.world.getYardDimension(), this.gameEngine.get().getYardDimension());
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
    public int getScore() {
        checkGame();
        return this.game.get().getScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProgressState() {
        checkGame();
        return this.game.get().getProgressState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placePlant(final CardView cardView, final int row, final int col) {
        checkGame();
        return this.game.get().placePlant(this.cards.get(cardView), row, col);
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
    public boolean buyPlant(final CardView cardView) {
        final boolean isBought = this.world.getShop().buyPlant(this.purchasablePlants.get(cardView));
        this.save();
        return isBought;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game startNewLevelGame(final int levelId) {
        resetGame();
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
        resetGame();
        this.game = Optional.of(this.world.createInfiniteGame());
        checkGame();
        checkGameEngine();
        this.gameEngine.get().loadCards(this.getCards());
        return this.game.get();
    }

    @Override
    public Set<EntityView> getPlacedEntities() {
        checkGame();
        final Set<Pair<String, Point2D>> plants = this.game.get().getPlacedPlants();
        final Set<Pair<String, Point2D>> zombies = this.game.get().getPlacedZombies();
        final Set<Pair<String, Point2D>> bullets = this.game.get().getPlacedBullet();

        //TODO: refactor
        //region Plants
        final Set<Pair<String, Point2D>> plantsToRemove = new HashSet<>();
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
                this.previousPlant.put(p, this.entityConverter.getPlantView(p));
            }
        });
        //endregion

        //region Zombies
        final Set<Pair<String, Point2D>> zombiesToRemove = new HashSet<>();
        this.previousZombie.keySet().forEach(z -> {
            if (!zombies.contains(z)) {
                zombiesToRemove.add(z);
            }
        });
        zombiesToRemove.forEach(z -> this.previousZombie.remove(z));
        zombies.forEach(z -> {
            if (this.previousZombie.containsKey(z)) {
                this.entityConverter.changeZombieViewPosition(this.previousZombie.get(z), z.getValue());
            } else {
                this.previousZombie.put(z, this.entityConverter.getZombieView(z));
            }
        });
        //endregion

        //region Bullets
        final Set<Pair<String, Point2D>> bulletToRemove = new HashSet<>();
        this.previousBullet.keySet().forEach(b -> {
            if (!bullets.contains(b)) {
                bulletToRemove.add(b);
            }
        });
        bulletToRemove.forEach(b -> this.previousBullet.remove(b));
        bullets.forEach(b -> {
            if (this.previousBullet.containsKey(b)) {
                this.entityConverter.changeBulletViewPosition(this.previousBullet.get(b), b.getValue());
            } else {
                this.previousBullet.put(b, this.entityConverter.getBulletView(b));
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
        this.game.get().getAllPlant()
                .forEach(p -> cards.put(CardGenerator.getCardView(p), p));
        return cards.keySet().stream().toList();
    }

    @Override
    public Set<CardView> getEnabledCards() {
        checkGame();
        final Set<Pair<String, Integer>> enabledPlants = this.game.get().getEnabledPlants();
        return this.cards.entrySet().stream()
                .filter(e -> enabledPlants.contains(e.getValue())) //Removed not available cardviews
                .map(Map.Entry::getKey) //Map to get just keys
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<CardView, Boolean> getPurchasablePlants() {
        Map<Pair<String,Integer>, Boolean> shopPlants = this.world.getShop().getPurchasablePlants();
        Map<CardView, Boolean> toReturn = new HashMap<>();
        this.purchasablePlants.clear();
        shopPlants.keySet().stream()
                .forEach(p -> {
                    CardView card = CardGenerator.getCardView(p);
                    this.purchasablePlants.put(card, p);
                    toReturn.put(card, shopPlants.get(p));
                });
        return Collections.unmodifiableMap(toReturn);
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

    private void resetGame() {
        this.cards.clear();
        this.previousPlant.clear();
        this.previousZombie.clear();
        this.previousBullet.clear();
        this.purchasablePlants.clear();
    }
}

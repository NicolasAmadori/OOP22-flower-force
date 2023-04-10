package flowerforce.controller;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import javafx.geometry.Point2D;

import java.util.function.Function;
import java.util.stream.Collectors;
import flowerforce.controller.utilities.CardGenerator;
import flowerforce.controller.utilities.EntityConverter;
import flowerforce.controller.utilities.WorldSavingManager;
import flowerforce.model.entities.EntityInfo;
import flowerforce.model.entities.plants.PlantInfo;
import flowerforce.model.game.Game;
import flowerforce.model.game.World;
import flowerforce.controller.utilities.EntityConverterImpl;
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
    private final Map<CardView, PlantInfo> cards = new HashMap<>();
    private final Map<EntityInfo, EntityView> previousPlant = new HashMap<>();
    private final Map<EntityInfo, EntityView> previousZombie = new HashMap<>();
    private final Map<EntityInfo, EntityView> previousBullet = new HashMap<>();
    private final Map<CardView, PlantInfo> purchasablePlants = new HashMap<>();

    /**
     * Create a new instance of Controller.
     */
    public ControllerImpl() {
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
        this.checkGameEngine();
        this.entityConverter = new EntityConverterImpl(this.world.getYardDimension(), this.gameEngine.get().getYardDimension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSunCounter() {
        this.checkGame();
        return this.game.get().getSun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        this.checkGame();
        return this.game.get().getScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProgressState() {
        this.checkGame();
        return this.game.get().getProgressState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placePlant(final CardView cardView, final int row, final int col) {
        this.checkGame();
        return this.game.get().placePlant(this.cards.get(cardView), row, col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removePlant(final int row, final int col) {
        this.checkGame();
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
    public void startNewAdventureModeGame(final int levelId) {
        this.resetGame();
        this.game = Optional.of(this.world.createAdventureModeGame(levelId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNewSurvivalModeGame() {
        this.resetGame();
        this.game = Optional.of(this.world.createSurvivalModeGame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityView> getPlacedPlants() {
        this.checkGame();
        updateEntities(this.previousPlant,
                this.game.get().getPlacedPlants(),
                (entityInfo) -> this.entityConverter.getPlantView(entityInfo),
                null);

        return new HashSet<>(this.previousPlant.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityView> getPlacedZombies() {
        this.checkGame();
        updateEntities(this.previousZombie,
                this.game.get().getPlacedZombies(),
                (entityInfo) -> this.entityConverter.getZombieView(entityInfo),
                (entityView, position) -> this.entityConverter.changeZombieViewPosition(entityView, position));

        return new HashSet<>(this.previousZombie.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityView> getPlacedBullets() {
        this.checkGame();
        updateEntities(this.previousBullet,
                this.game.get().getPlacedBullet(),
                (entityInfo) -> this.entityConverter.getBulletView(entityInfo),
                (entityView, position) -> this.entityConverter.changeBulletViewPosition(entityView, position));

        return new HashSet<>(this.previousBullet.values());
    }

    /*
    * This method update the entities map, removing old entities, update previous entities and adding new entities
    */
    private void updateEntities(Map<EntityInfo, EntityView> previousEntities,
                                Set<EntityInfo> updatedEntities,
                                Function<EntityInfo, EntityView> entityConverter,
                                BiConsumer<EntityView, Point2D> positionConverter) {

        final Set<EntityInfo> entitiesToRemove = new HashSet<>();
        previousEntities.keySet().forEach(z -> {
            if (!updatedEntities.contains(z)) {
                entitiesToRemove.add(z);
            }
        });
        entitiesToRemove.forEach(previousEntities::remove);
        updatedEntities.forEach(e -> {
            if (previousEntities.containsKey(e)) {
                if(positionConverter != null) {
                    positionConverter.accept(previousEntities.get(e), e.getPosition());
                }
            } else {
                previousEntities.put(e, entityConverter.apply(e));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EntityView> getDamagedEntities() {
        checkGame();
        final var damagedEntities = this.game.get().getDamagedEntity();
        final Set<EntityView> output = new HashSet<>();
        this.previousPlant.entrySet().stream()
                .filter(e -> damagedEntities.contains(e.getKey()))
                .forEach(e -> output.add(e.getValue()));
        this.previousZombie.entrySet().stream()
                .filter(e -> damagedEntities.contains(e.getKey()))
                .forEach(e -> output.add(e.getValue()));
        return output;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<CardView> getEnabledCards() {
        this.checkGame();
        final Set<PlantInfo> enabledPlants = this.game.get().getEnabledPlants();
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
        final Map<PlantInfo, Boolean> shopPlants = this.world.getShop().getPlants();
        final Map<CardView, Boolean> toReturn = new HashMap<>();
        this.purchasablePlants.clear();
        shopPlants.keySet().stream()
                .forEach(p -> {
                    final CardView card = CardGenerator.getCardView(p);
                    this.purchasablePlants.put(card, p);
                    toReturn.put(card, shopPlants.get(p));
                });
        return Collections.unmodifiableMap(toReturn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalRows() {
        return this.world.getRowsNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalColumns() {
        return this.world.getColsNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        WorldSavingManager.save(this.world);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Runnable getGameLoopRunnable() {
        checkGame();
        checkGameEngine();
        this.gameEngine.get().loadCards(this.getCards());
        return new GameLoop(this.gameEngine.get(), this.game.get(), this.world.getRenderingInformations());
    }

    private List<CardView> getCards() {
        this.checkGame();
        this.game.get().getPlaceablePlant()
                .forEach(p -> cards.put(CardGenerator.getCardView(p), p));
        return cards.keySet().stream().toList();
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
    }
}

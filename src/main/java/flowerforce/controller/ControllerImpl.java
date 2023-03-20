package flowerforce.controller;

import flowerforce.common.WorldSavingManager;
import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import flowerforce.model.game.Game;
import flowerforce.model.game.World;
import flowerforce.model.game.Yard;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.EntityConverter;
import flowerforce.view.entities.EntityView;
import flowerforce.view.game.GameEngine;

import java.util.*;

/**
 * This is an implementation of {@link Controller}.
 */
public final class ControllerImpl implements Controller {

    private GameEngine gameEngine;
    private final World world;

    private Game game;

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
    public int getLastUnlockedLevelId() {
        return this.world.getPlayer().getLastUnlockedLevelId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSunCounter() {
        if (this.game != null) {
            return this.game.getSun();
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placePlant(final int plantId, final int row, final int col) {
        if(this.game != null) {
            this.game.placePlant(plantId, row, col);//implement this when game is corrected
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNewLevelGame(final int levelId) {
        this.game = this.world.createLevelGame(levelId);
        final GameLoop gameLoop = new GameLoopImpl(this.gameEngine, this.game); //TODO: update
        new Thread((Runnable) gameLoop).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNewInfiniteGame() {
        //final GameLoop gameLoop = new GameLoopImpl(GameEngine, this.world.StartNewGame());
        //new Thread((Runnable) gameLoop).start();
    }

    @Override
    public Set<EntityView> getPlacedEntities() {
        final Set<Plant> plants = this.game.getPlacedPlants();
        final Set<Zombie> zombies = this.game.getZombies();
        final Set<Bullet> bullets = this.game.getBullet();

        final Set<EntityView> output = new HashSet<>();
        plants.forEach(p -> output.add(EntityConverter.getEntityView(p)));
        zombies.forEach(z -> output.add(EntityConverter.getEntityView(z)));
        bullets.forEach(z -> output.add(EntityConverter.getEntityView(z)));

        return output;
    }

    @Override
    public List<CardView> getCards() {
        if(game != null) {
            final List<IdConverter.Plants> plants = this.game.getAllPlantIDs();
            final List<CardView> cards = new ArrayList<>();
            plants.forEach(p -> cards.add(EntityConverter.getCardView(p)));
            return cards;
        }
        return new ArrayList<>();
    }

    @Override
    public Set<Integer> getEnabledCards() {
        if(this.game != null) {
            return this.game.getAvailablePlantsIDs();//uncomment this when game is corrected
        }
        return Set.of();
    }

    @Override
    public int getTotalRows() {
        return Yard.getRowsNum();
    }

    @Override
    public int getTotalColumns() {
        return Yard.getColsNum();
    }
}

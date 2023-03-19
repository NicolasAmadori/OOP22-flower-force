package flowerforce.controller;

import flowerforce.common.WorldSavingManager;
import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import flowerforce.model.game.Game;
import flowerforce.model.game.World;
import flowerforce.view.entities.EntityConverter;
import flowerforce.view.entities.EntityView;
import flowerforce.view.game.GameEngine;

import javafx.geometry.Dimension2D;

import java.util.HashSet;
import java.util.Set;

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
    public ControllerImpl() throws InstantiationException{
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
        if(this.game != null) {

            return this.game.getSun();
        }
        //return this.game.getSun();

        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placePlant(final IdConverter.Plants p, final int row, final int col) {
        //this.world.placePlant(row, col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNewLevelGame(final int levelId) {
        this.game = this.world.createLevelGame(1);
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
        Set<Plant> plants = this.game.getPlants();
        Set<Zombie> zombies = this.game.getZombies();
        Set<Bullet> bullets = this.game.getBullet();

        Set<EntityView> output = new HashSet<>();
        plants.forEach(p -> output.add(EntityConverter.getEntityView(p)));
        zombies.forEach(z -> output.add(EntityConverter.getEntityView(z)));
        bullets.forEach(z -> output.add(EntityConverter.getEntityView(z)));

        return output;
    }
}

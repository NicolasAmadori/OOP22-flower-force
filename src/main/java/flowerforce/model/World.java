package flowerforce.model;

import flowerforce.model.game.Game;
import flowerforce.model.game.GameImpl;

public class World {

    private int nCoins;
    private int nSun;

    public World(int nCoins) {
        this.nCoins = nCoins;
        this.nSun = 0;
    }

    public int getCoins() {
        return this.nCoins;
    }

    public int getSunCounter() {
        return this.nSun;
    }

    public void placePlant(int row, int col){
        this.nSun = row * 10 + col;
    }
    public void update() {

    }

    public Game createGame(int levelId) {
        //return new GameImpl();
        return null;
    }
}

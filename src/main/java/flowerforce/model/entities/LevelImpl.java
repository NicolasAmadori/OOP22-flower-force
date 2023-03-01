package flowerforce.model.entities;

import java.util.List;

public class LevelImpl implements Level{

    private final List<Integer> avaiableZombies;
    private final List<Integer> avaiablePlants;
    private final int coins;
    private final int row;
    private final int nZombie;

    public LevelImpl(List<Integer> avaiableZombies, List<Integer> avaiablePlants, int coins, int nZombie, int row) {
        this.coins = coins;
        this.avaiablePlants = avaiablePlants;
        this.avaiableZombies = avaiableZombies;
        this.row = row;
        this.nZombie = nZombie;
    }

    @Override
    public int getLevelCoins() {
        return this.coins;
    }

    @Override
    public List<Integer> getPlantsId() {
        return this.avaiablePlants;
    }
    
    @Override
    public List<Integer> getZombiesId() {
        return this.avaiableZombies;
    }

    @Override
    public Integer getNumberOfRowAvaiable() {
        return this.row;
    }

    @Override
    public Integer getTotalZombies() {
        return this.nZombie;
    }
}

package flowerforce.model.game;

import flowerforce.model.entities.IdConverter;

public class BuyablePlant {
    private final IdConverter.Plants plant;
    private final int cost;

    public BuyablePlant(final IdConverter.Plants plant, final int cost) {
        this.plant = plant;
        this.cost = cost;
    }

    public int getId() {
        return this.plant;
    }

    public int getCost() {
        return this.cost;
    }
}
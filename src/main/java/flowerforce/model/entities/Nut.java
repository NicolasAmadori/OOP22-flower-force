package flowerforce.model.entities;

import flowerforce.common.Timer;
import flowerforce.model.entities.IdConverter.Plants;
import javafx.geometry.Point2D;

/**
 * Models a Nut, a tank plant that loses life the more times passes.
 */
public class Nut extends AbstractPlant {

    private static final int NUT_HEALTH = 5000;
    private static final int AUTO_DAMAGE = 5;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     * @param plantType the type of the plant
     */
    public Nut(final Point2D pos, final Timer timer, final Plants plantType) {
        super(pos, timer, NUT_HEALTH, plantType);
    }

    /**
     * Gets auto-damage.
     */
    @Override
    public void updateState() {
        super.updateState();
        this.receiveDamage(AUTO_DAMAGE);
    }

}

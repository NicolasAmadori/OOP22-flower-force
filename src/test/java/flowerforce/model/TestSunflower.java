package flowerforce.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flowerforce.model.entities.plants.Sunflower;
import flowerforce.model.entities.plants.SunflowerFactory;
import flowerforce.model.utilities.RenderingInformation;
import javafx.geometry.Point2D;

final class TestSunflower {

    private static final double STANDARD_SECS_SUN_PRODUCING_TIME = 24.25;
    private static final int STANDARD_SUNPRODUCING_TIME = RenderingInformation.convertSecondsToCycles(
        STANDARD_SECS_SUN_PRODUCING_TIME
    );
    private static final Point2D STARTING_PLANT_POS = new Point2D(100.0, 100.0);
    private Sunflower sunflower = SunflowerFactory.createCommonSunflower(STARTING_PLANT_POS);

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setup() {
        this.sunflower = SunflowerFactory.createCommonSunflower(STARTING_PLANT_POS);
    }

    /**
     * Tests plants' damage reception.
     */
    @Test
    void testReceiveDamage() {
        final int damage = 10;
        final int sunflowerStartingHealth = this.sunflower.getHealth();
        this.sunflower.receiveDamage(damage);
        assertEquals(sunflowerStartingHealth - damage, this.sunflower.getHealth());
    }

    @Test 
    void testSunProduction() {
        for (int i = 0; i < STANDARD_SUNPRODUCING_TIME - 1; i++) {
            this.sunflower.updateState();
            assertFalse(this.sunflower.isSunGenerated());
        }
        this.sunflower.updateState();
        assertTrue(this.sunflower.isSunGenerated());
        this.sunflower.updateState();
        assertFalse(this.sunflower.isSunGenerated());
    }
}

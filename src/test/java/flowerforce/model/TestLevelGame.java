package flowerforce.model;

import flowerforce.model.entities.Plant;
import flowerforce.model.entities.PlantInfoImpl;
import flowerforce.model.entities.SunflowerFactory;
import flowerforce.model.game.*;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestLevelGame {

    Player player;
    World world;
    Game game;
    private static final Plant sunflower =
            SunflowerFactory.createCommonSunflower(new Point2D(0,0));
    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        player = new PlayerImpl();
        world = new WorldImpl(Optional.of(this.player));
        game = world.createAdventureModeGame(this.player.getLastUnlockedLevelId());
    }

    /**
     * Test the player's coins management
     */
    @Test
    void testPlacedPlant() {
        assertEquals(Set.of(), this.game.getPlacedPlants());

        final var placeable = this.game.getPlaceablePlant().stream().findFirst();
        assertNotEquals(Optional.empty(), placeable);
        assertTrue(this.game.placePlant(placeable.get(), 0, 0));
        assertEquals(1, this.game.getPlacedPlants().size());

        assertFalse(this.game.placePlant(placeable.get(), 0, 0));
        assertEquals(1, this.game.getPlacedPlants().size());
    }

    @Test
    void test() {
        assertEquals(Set.of(), this.game.getDamagedEntity());

    }

}

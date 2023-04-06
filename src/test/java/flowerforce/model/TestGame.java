package flowerforce.model;

import flowerforce.model.game.Game;
import flowerforce.model.game.Player;
import flowerforce.model.game.World;
import flowerforce.model.game.WorldImpl;
import flowerforce.model.game.PlayerImpl;
import flowerforce.model.utilities.RenderingInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class TestGame {
    private static final double STANDARD_SECS_SPAWN_SUN = 5.0;
    private static final int TIME_TO_SPAWN_SUN = (int)
            (STANDARD_SECS_SPAWN_SUN * RenderingInformation.getFramesPerSecond());
    private static final int START_SUN_GAME = 50;
    private static final int SUN_VALUE = 25;
    private Game game;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        final Player player = new PlayerImpl();
        final World world = new WorldImpl(Optional.of(player));
        game = world.createAdventureModeGame(player.getLastUnlockedLevelId());
    }

    /**
     * Test the place plant management.
     */
    @Test
    void testPlacedPlant() {
        //at the beginning there are 0 plants placed
        assertEquals(Set.of(), this.game.getPlacedPlants());

        //in the beginning there will be only the sunflower
        final var placeable = this.game.getEnabledPlants().stream().findFirst();
        assertNotEquals(Optional.empty(), placeable);

        //should return true because there is no plant placed
        assertTrue(this.game.placePlant(placeable.get(), 0, 0));
        assertEquals(1, this.game.getPlacedPlants().size());

        //should return false because I am trying to place a plant where I have already placed it
        assertFalse(this.game.placePlant(placeable.get(), 0, 0));
        assertEquals(1, this.game.getPlacedPlants().size());

        //should return true because there is a plant
        assertTrue(this.game.removePlant(0, 0));
        assertEquals(0, this.game.getPlacedPlants().size());
    }

    /**
     * Test the generation sun management.
     */
    @Test
    void testSun() {
        int sun = START_SUN_GAME;
        //at the beginning there are 2 sunflowers
        assertEquals(sun, this.game.getSun());

        for (int i = 0; i < TIME_TO_SPAWN_SUN * 2; i++) {
            this.game.update();
        }
        sun += SUN_VALUE * 2;

        //after 10 seconds the field will have produced 2 more suns
        assertEquals(sun, this.game.getSun());

        //placing a plant will decrease the number of suns
        final var placeable = this.game.getPlaceablePlant().stream()
                .filter(p -> p.getCost() <= START_SUN_GAME).findFirst();
        assertNotEquals(Optional.empty(), placeable);
        assertTrue(this.game.placePlant(placeable.get(), 0, 0));
        sun -= placeable.get().getCost();
        assertEquals(sun, this.game.getSun());
    }

    /**
     * Test which plant can place.
     */
    @Test
    void testEnabledPlant() {
        //at the beginning there is only the sunflower ready to be placed
        assertEquals(1, this.game.getEnabledPlants().size());

        //after 10 seconds the field will have produced 2 more suns
        for (int i = 0; i < TIME_TO_SPAWN_SUN * 2; i++) {
            this.game.update();
        }
        //so the number of plants that can be placed should be 2
        assertEquals(2, this.game.getEnabledPlants().size());

        final var placeable = this.game.getPlaceablePlant().stream().findFirst();
        assertNotEquals(Optional.empty(), placeable);
        assertTrue(this.game.placePlant(placeable.get(), 0, 0));

        //now the number of plants that can be placed should be 0
        assertEquals(0, this.game.getEnabledPlants().size());
    }

    @Test
    void testEndGame() {
        //at the beginning is over should be false
        assertFalse(this.game.isOver());

        while (!this.game.isOver()) {
            this.game.update();
        }
        assertTrue(this.game.isOver());

        //should return false because there is no plant to stop the zombies,
        //so the game will be lost
        assertFalse(this.game.result());
    }
}

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
        assertEquals(Set.of(), this.game.getPlacedPlants());

        final var placeable = this.game.getPlaceablePlant().stream().findFirst();
        assertNotEquals(Optional.empty(), placeable);
        assertTrue(this.game.placePlant(placeable.get(), 0, 0));
        assertEquals(1, this.game.getPlacedPlants().size());

        assertFalse(this.game.placePlant(placeable.get(), 0, 0));
        assertEquals(1, this.game.getPlacedPlants().size());

        assertTrue(this.game.removePlant(0, 0));
        assertEquals(0, this.game.getPlacedPlants().size());
    }

    /**
     * Test the generation sun management.
     */
    @Test
    void testSun() {
        int sun = START_SUN_GAME;
        assertEquals(sun, this.game.getSun());

        for (int i = 0; i < TIME_TO_SPAWN_SUN * 2; i++) {
            this.game.update();
        }

        sun += SUN_VALUE;
        assertEquals(sun, this.game.getSun());

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
        assertEquals(1, this.game.getEnabledPlants().size());

        for (int i = 0; i < TIME_TO_SPAWN_SUN * 2; i++) {
            this.game.update();
        }
        assertEquals(2, this.game.getEnabledPlants().size());
        final var placeable = this.game.getPlaceablePlant().stream().findFirst();
        assertNotEquals(Optional.empty(), placeable);
        assertTrue(this.game.placePlant(placeable.get(), 0, 0));
        assertEquals(0, this.game.getEnabledPlants().size());
    }
}

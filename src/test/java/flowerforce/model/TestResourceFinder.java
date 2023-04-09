package flowerforce.model;

import flowerforce.common.ResourceFinder;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.net.URISyntaxException;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test class for the ResourceFinder.
 */
final class TestResourceFinder {

    /**
     * Test the sounds resource.
     */
    @Test
    void testSounds() {
        assertDoesNotThrow(() -> ResourceFinder.getSoundPath("shovel.wav"));
    }

    /**
     * Test the images resource.
     */
    @Test
    void testImages() {
        try {
            final File f = new File(ResourceFinder.getPlantImagePath("sunflower.png").toURI());
            assertTrue(f.exists());
            assertEquals("sunflower.png", f.getName());
        } catch (URISyntaxException e) {
            fail("The resource has not been found");
        }
    }

    /**
     * Test the saving resource.
     */
    @Test
    void testSavingFile() {
        final File f = new File(ResourceFinder.getSavingFilePath("player.json"));
        assertTrue(f.exists());
        assertEquals("player.json", f.getName());
    }
}

package flowerforce.model;

import flowerforce.common.ResourceFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the ResourceFinder.
 */
final class TestResourceFinder {

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setup() {

    }

    /**
     * Test the sounds resource.
     */
    @Test
    void testSounds() {
        try {
            final File f = new File(ResourceFinder.getSoundPath("shovel.wav").toURI());
            assertTrue(f.exists());
            assertEquals("shovel.wav", f.getName());
        } catch (URISyntaxException e) {
            fail("The resource has not been found");
        }
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
}

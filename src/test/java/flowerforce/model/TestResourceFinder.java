package flowerforce.model;

import flowerforce.common.ResourceFinder;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the ResourceFinder.
 */
final class TestResourceFinder {

    /**
     * Test the sounds resource finding.
     */
    @Test
    void testSounds() {
        assertDoesNotThrow(() -> ResourceFinder.getSoundURL("shovel.wav"));
        assertThrows(NoSuchElementException.class, () -> ResourceFinder.getSoundURL("notARealSound.wav"));
    }

    /**
     * Test the images resource finding.
     */
    @Test
    void testImages() {
        try {
            final File firstFile = new File(assertDoesNotThrow(() -> ResourceFinder.getPlantImageURL("sunflower.png")).toURI());
            assertTrue(firstFile.exists());
            assertEquals("sunflower.png", firstFile.getName());

            assertThrows(NoSuchElementException.class, () -> ResourceFinder.getPlantImageURL("notAnImage.png"));

        } catch (URISyntaxException e) {
            fail("The resource has not been found");
        }
    }

    /**
     * Test the saving resource finding.
     */
    @Test
    void testSavingFile() {
        final File f = new File(assertDoesNotThrow(() -> ResourceFinder.getSavingFilePath("player.json")));
        //it could not exist if there is no saving file
        if (f.exists()) {
            assertEquals("player.json", f.getName());
        }
    }

    /**
     * Test the fxml resource finding.
     */
    @Test
    void testFXMLFile() {
        assertDoesNotThrow(() -> ResourceFinder.getFXMLURL("Game.fxml"));

        assertThrows(NoSuchElementException.class, () -> ResourceFinder.getFXMLURL("NotAFXML.fxml"));
    }
}

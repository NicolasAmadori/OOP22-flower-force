package flowerforce.model.utilities;

/**
 * Utility class to get the rendering information.
 */
public final class RenderingInformation {

    private static final int FRAMES_PER_SECOND = 30;

    private RenderingInformation() {

    }

    /**
     * Get the frames per second the game must be set.
     * @return an integer representing the maximum frames per second
     */
    public static int getFramesPerSecond() {
        return FRAMES_PER_SECOND;
    }
}

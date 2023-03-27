package flowerforce.model.utilities;

import flowerforce.model.game.Yard;

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

    /**
     * Converts from seconds an entity uses to cross a cell to position
     * it has to move each frame.
     * @param secondsPerCell seconds an entity uses to cross a cell
     * @return positions it has to move each frame
     */
    public static int getDeltaFromSecondsPerCell(double secondsPerCell) {
        final int ticksPerCell = (int) (secondsPerCell * RenderingInformation.getFramesPerSecond());
        final int delta = (int) (Yard.getCellDimension().getWidth() / ticksPerCell);
        return delta;
    }

    /**
     * Convert seconds to number of frames in that time
     * @param seconds
     * @return number of frames
     */
    public static int convertSecondsToCycles(double seconds) {
        return (int) (seconds * RenderingInformation.getFramesPerSecond());
    } 
}

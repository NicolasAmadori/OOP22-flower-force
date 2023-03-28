package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

public class SunflowerFactoryImpl implements SunflowerFactory {

    private static final double STANDARD_SECS_SHOOTING_TIME = 1.425;
    private static final int STANDARD_SUNPRODUCING_TIME = (int) (
        STANDARD_SECS_SHOOTING_TIME * RenderingInformation.getFramesPerSecond()
    );
    private static final double STANDARD_SUNFLOWER_HEALTH = 300.0;

    @Override
    public Sunflower createCommonSunflower(final Point2D pos, final Plants plantType) {
        return new SunflowerImpl(pos, new TimerImpl(STANDARD_SUNPRODUCING_TIME), STANDARD_SUNFLOWER_HEALTH, plantType);
    }

    @Override
    public Sunflower createDoubleSunflower(final Point2D pos, final Plants plantType) {
        return new SunflowerImpl(pos, new TimerImpl(STANDARD_SUNPRODUCING_TIME / 2), STANDARD_SUNFLOWER_HEALTH, plantType);
    }
    
}

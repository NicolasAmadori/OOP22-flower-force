package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Plants;
import javafx.geometry.Point2D;

public interface SunflowerFactory {
    
    Sunflower createCommonSunflower(Point2D pos, Plants plantType);

    Sunflower createDoubleSunflower(Point2D pos, Plants plantType);
}

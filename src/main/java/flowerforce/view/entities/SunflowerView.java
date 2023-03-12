package flowerforce.view.entities;

import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.scene.image.Image;

public class SunflowerView implements PlantView {

    private static final String IMAGE_PATH = "flowerforce/game/images/sunflower.gif";
    private final Image image;
    private final GenericValues resistance;
    private final int cost;

    public SunflowerView(final int cost) throws FileNotFoundException {
        this.image = new Image(ClassLoader.getSystemResourceAsStream(IMAGE_PATH));
        this.resistance = GenericValues.MEDIUM;
        this.cost = cost;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public GenericValues getResistanceValue() {
        return this.resistance;
    }

    @Override
    public Optional<GenericValues> getDamageValue() {
        return Optional.empty();
    }

    @Override
    public int getCost() {
        return cost;
    }
}

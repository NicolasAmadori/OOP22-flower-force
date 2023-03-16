package flowerforce.view.entities;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;

import java.io.File;

public class EntityConverterImpl implements EntityConverter {
    private final static String IMAGES_FOLDER_PATH = "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "flowerforce" + File.separator + "game" + File.separator + "images";

    private final static String IMAGES_EXTENSION = ".gif";
    @Override
    public EntityView getEntityView(Plant p) {
        final String completeimagePath = IMAGES_FOLDER_PATH + File.separator
                + p.getPlantType().name().toLowerCase() + IMAGES_EXTENSION;
        return new EntityViewImpl(p.getPosition(), completeimagePath);
    }

    @Override
    public EntityView getEntityView(Zombie z) {
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + z.getZombieType().name().toLowerCase() + IMAGES_EXTENSION;
        return new EntityViewImpl(z.getPosition(), completeImagePath);
    }

    @Override
    public EntityView getEntityView(Bullet b) {
        final String bulletName = getName(b.getClass().getName().toLowerCase());
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + bulletName + IMAGES_EXTENSION;
        return new EntityViewImpl(b.getPosition(), completeImagePath);
    }

    private String getName(String completePackage) {
        String[] splitted = completePackage.split("\\.");
        return splitted[splitted.length - 1];
    }
}

package flowerforce;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.StandardBullet;
import flowerforce.view.game.FlowerForceApplication;
import javafx.geometry.Point2D;

import static javafx.application.Application.launch;

/**
 * Program's main class.
 */
public final class FlowerForce {

    private FlowerForce() {
    }

    /**
     * Program's entry point.
     * @param args
     */
    public static void main(final String[] args) {
        launch(FlowerForceApplication.class, args);
    }
}

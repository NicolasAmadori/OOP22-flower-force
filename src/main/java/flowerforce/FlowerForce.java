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
        Bullet b = new StandardBullet(new Point2D(0, 0));
        System.out.println(b.getClass().getName());
    }
}

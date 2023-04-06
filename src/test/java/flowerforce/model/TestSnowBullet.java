package flowerforce.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.ShootingPlant;
import flowerforce.model.entities.ShootingPlantFactory;
import flowerforce.model.entities.Zombie;
import flowerforce.model.entities.ZombieFactory;
import javafx.geometry.Point2D;


final class TestSnowBullet {

    private static final double STARTING_PLANT_POS_X = 100.0;
    private static final double STARTING_PLANT_POS_Y = 100.0;

    private final ShootingPlant producer = ShootingPlantFactory.createSnowShooter(
        new Point2D(STARTING_PLANT_POS_X, STARTING_PLANT_POS_Y)
    );
    private Zombie zombie;
    private Optional<Bullet> bullet;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        this.zombie = ZombieFactory.basic(new Point2D(STARTING_PLANT_POS_X + 1, STARTING_PLANT_POS_Y));
        this.bullet = Optional.empty();
        while (this.bullet.isEmpty()) {
            this.producer.updateState();
            bullet = this.producer.nextBullet();
        }
    }

    /**
     * Tests bullets' movement.
     */
    @Test
    void testMove() {
        assertEquals(new Point2D(STARTING_PLANT_POS_X, STARTING_PLANT_POS_Y), this.bullet.get().getPosition());
        this.bullet.get().move();
        assertEquals(
            new Point2D(STARTING_PLANT_POS_X + this.bullet.get().getDeltaMovement(), STARTING_PLANT_POS_Y),
            this.bullet.get().getPosition()
        );
        this.bullet.get().move();
        assertEquals(
            new Point2D(STARTING_PLANT_POS_X + 2 * this.bullet.get().getDeltaMovement(), STARTING_PLANT_POS_Y),
            this.bullet.get().getPosition()
        );
        this.bullet.get().move();
        assertEquals(
            new Point2D(STARTING_PLANT_POS_X + 3 * this.bullet.get().getDeltaMovement(), STARTING_PLANT_POS_Y),
            this.bullet.get().getPosition()
        );
    }

    /**
     * Tests bullets' hit to a zombie.
     */
    @Test
    void testHit() {
        final double startingZombieHealth = this.zombie.getHealth(); 
        this.bullet.get().hit(this.zombie);
        assertNotEquals(startingZombieHealth, this.zombie.getHealth());
        assertTrue(this.bullet.get().isOver());
    }

    @Test
    void testFreeze() {
        final double startingPos = this.zombie.getPosition().getX();
        this.zombie.move();
        final double beforeHitPos = this.zombie.getPosition().getX();
        this.bullet.get().hit(this.zombie);
        this.zombie.move();
        final double afterHitPos = this.zombie.getPosition().getX();
        assertTrue(Math.abs(startingPos - beforeHitPos) > Math.abs(afterHitPos - beforeHitPos));
    }
}

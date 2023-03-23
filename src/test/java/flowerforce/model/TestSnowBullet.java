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
import flowerforce.model.entities.ShootingPlantFactoryImpl;
import flowerforce.model.entities.Zombie;
import flowerforce.model.entities.ZombieFactory;
import flowerforce.model.entities.ZombieFactoryImpl;
import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.entities.IdConverter.Zombies;
import javafx.geometry.Point2D;


final class TestSnowBullet {

    private static final double STARTING_BULLET_POS_X = 1.0;
    private static final double STARTING_BULLET_POS_Y = 0.0;
    private static final double STARTING_PLANT_POS_X = 0.0;
    private static final double STARTING_PLANT_POS_Y = 0.0;

    private final ShootingPlantFactory plantFactory = new ShootingPlantFactoryImpl();
    private final ZombieFactory zombieFactory = new ZombieFactoryImpl();
    private final ShootingPlant producer = this.plantFactory.snow(
        new Point2D(STARTING_PLANT_POS_X, STARTING_PLANT_POS_Y),
        Plants.SNOWSHOOTER
    );
    private Zombie zombie;
    private Optional<Bullet> bullet;

    /**
     * Sets up the testing.
     */
    @BeforeEach
    void setUp() {
        this.zombie = zombieFactory.basic(new Point2D(STARTING_BULLET_POS_X + 1, STARTING_BULLET_POS_Y), Zombies.BASIC);
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
        assertEquals(new Point2D(STARTING_BULLET_POS_X, STARTING_BULLET_POS_Y), this.bullet.get().getPosition());
        this.bullet.get().move();
        assertEquals(
            new Point2D(STARTING_BULLET_POS_X + this.bullet.get().getDeltaMovement(), STARTING_BULLET_POS_Y),
            this.bullet.get().getPosition()
        );
        this.bullet.get().move();
        assertEquals(
            new Point2D(STARTING_BULLET_POS_X + 2 * this.bullet.get().getDeltaMovement(), STARTING_BULLET_POS_Y),
            this.bullet.get().getPosition()
        );
        this.bullet.get().move();
        assertEquals(
            new Point2D(STARTING_BULLET_POS_X + 3 * this.bullet.get().getDeltaMovement(), STARTING_BULLET_POS_Y),
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
}

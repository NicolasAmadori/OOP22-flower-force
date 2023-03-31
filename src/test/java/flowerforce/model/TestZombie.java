package flowerforce.model;

import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Zombie;
import flowerforce.model.game.Yard;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class TestZombie {
    private static final double INITIAL_X = Yard.getYardDimension().getWidth();
    private static final double INITIAL_Y = Yard.getYardDimension().getHeight();
    private static final double FREEZE_FACTOR = 2; //Freeze factor of ZombieImpl
    private static final int DAMAGE = 10;
    private Zombie zombie;

    @BeforeEach
    public void setup() {
        this.zombie = IdConverter.createZombie(IdConverter.Zombies.BASIC, new Point2D(INITIAL_X, INITIAL_Y));
    }

    @Test
    public void testMove() {
        double expectedPosX = INITIAL_X;
        assertEquals(new Point2D(expectedPosX, INITIAL_Y), this.zombie.getPosition()); //Check initial pos
        this.zombie.move();
        expectedPosX -= this.zombie.getDeltaMovement();
        assertEquals(new Point2D(expectedPosX, INITIAL_Y), this.zombie.getPosition());
        this.zombie.move();
        expectedPosX -= this.zombie.getDeltaMovement();
        this.zombie.move();
        expectedPosX -= this.zombie.getDeltaMovement();
        assertEquals(new Point2D(expectedPosX, INITIAL_Y), this.zombie.getPosition());
    }

    @Test
    public void testFreeze() {
        double expectedDelta = this.zombie.getDeltaMovement();
        assertEquals(expectedDelta, this.zombie.getDeltaMovement());
        this.zombie.freeze();
        expectedDelta = expectedDelta / FREEZE_FACTOR;
        assertEquals(expectedDelta, this.zombie.getDeltaMovement());
        this.zombie.freeze();
        this.zombie.freeze();
        //delta must remain the same because the zombie is already frozen
        assertEquals(expectedDelta, this.zombie.getDeltaMovement());
    }

    @Test
    public void testReceiveDamage() {
        int expectedHealth = this.zombie.getHealth();
        assertEquals(expectedHealth, this.zombie.getHealth());
        this.zombie.receiveDamage(DAMAGE);
        expectedHealth -= DAMAGE;
        assertEquals(expectedHealth, this.zombie.getHealth());
        this.zombie.receiveDamage(2 * DAMAGE);
        expectedHealth -= 2 * DAMAGE;
        assertEquals(expectedHealth, this.zombie.getHealth());
    }
}

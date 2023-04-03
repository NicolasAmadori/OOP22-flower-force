package flowerforce.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import flowerforce.model.entities.EntityInfo;
import javafx.geometry.Point2D;

public final class TestEntityInfo {
    
    private static final Point2D pos1 = new Point2D(10, 20);
    private static final String str1 = "test";
    private static final int cost1 = 100;


    @Test
    public void testEqualsWithPos() {
        final EntityInfo<String,Point2D> eInfo1 = new EntityInfo<>(str1, pos1);
        final EntityInfo<String,Point2D> eInfo2 = new EntityInfo<>(str1, pos1);
        assertNotEquals(eInfo1, eInfo2);
        assertFalse(eInfo1.equals(eInfo2));
        final EntityInfo<String,Point2D> eInfo3 = eInfo1;
        assertEquals(eInfo1, eInfo3);
        assertTrue(eInfo1.equals(eInfo3));
    }

    @Test
    public void testEqualsWithCost() {
        final EntityInfo<String,Integer> eInfo1 = new EntityInfo<>(str1, cost1);
        final EntityInfo<String,Integer> eInfo2 = new EntityInfo<>(str1, cost1);
        assertNotEquals(eInfo1, eInfo2);
        assertFalse(eInfo1.equals(eInfo2));
        final EntityInfo<String,Integer> eInfo3 = eInfo1;
        assertEquals(eInfo1, eInfo3);
        assertTrue(eInfo1.equals(eInfo3));
    }
}

package flowerforce.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import flowerforce.model.entities.EntityInfo;
import flowerforce.model.entities.EntityInfoImpl;
import flowerforce.model.entities.PlantInfo;
import flowerforce.model.entities.PlantInfoImpl;
import flowerforce.model.entities.Zombie;
import flowerforce.model.entities.ZombieFactory;
import javafx.geometry.Point2D;

/**
 * Tests for classes {@link EntityInfo} and {@link PlantInfo}. 
 */
public final class TestInfo {

    private static final Point2D POS_TEST = new Point2D(10, 20);
    private static final String NAME_TEST = "test";
    private static final int COST_TEST = 100;

    /**
     * Tests the uniqueness of {@link EntityInfo}, so that it can be used as key of maps
     * even if its fields are the same of others {@link EntityInfo} 
     * (name and position could be the same to different entities).
     */
    @Test
    public void testEqualsEntityInfo() {
        final EntityInfo eInfo1 = new EntityInfoImpl(NAME_TEST, POS_TEST);
        final EntityInfo eInfo2 = new EntityInfoImpl(NAME_TEST, POS_TEST);
        assertNotEquals(eInfo1, eInfo2);
        assertFalse(eInfo1.equals(eInfo2));
        final EntityInfo eInfo3 = eInfo1;
        assertEquals(eInfo1, eInfo3);
        assertTrue(eInfo1.equals(eInfo3));
    }

    /**
     * Tests the uniqueness of {@link PlantInfo}, so that it can be used as key of maps
     * even if its fields are the same of others {@link PlantInfo}.
     */
    @Test
    public void testEqualsPlantInfo() {
        final PlantInfo eInfo1 = new PlantInfoImpl(NAME_TEST, COST_TEST);
        final PlantInfo eInfo2 = new PlantInfoImpl(NAME_TEST, COST_TEST);
        assertNotEquals(eInfo1, eInfo2);
        assertFalse(eInfo1.equals(eInfo2));
        final PlantInfo eInfo3 = eInfo1;
        assertEquals(eInfo1, eInfo3);
        assertTrue(eInfo1.equals(eInfo3));
    }

    /**
     * Tests the correct and automatic update of position in a {@link EntityInfo}
     * using {@link EntityInfo#setPosition(Point2D)} method.
     */
    @Test
    public void testMoveEntityInfo() {
        final Zombie basic = ZombieFactory.basic(POS_TEST);
        final EntityInfo e1 = basic.getEntityInfo();
        assertEquals(e1, basic.getEntityInfo());
        basic.move();
        assertEquals(e1, basic.getEntityInfo()); //when moved, entityInfo must remain equal
        assertEquals(e1.getPosition(), basic.getPosition()); //but also must update its position field
    }
}

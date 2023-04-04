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
import flowerforce.model.entities.ZombieFactoryImpl;
import javafx.geometry.Point2D;

public final class TestInfo {
    
    private static final Point2D pos1 = new Point2D(10, 20);
    private static final String str1 = "test";
    private static final int cost1 = 100;
    private static final ZombieFactory ZOMBIE_FACTORY = new ZombieFactoryImpl();

    @Test
    public void testEqualsEntityInfo() {
        final EntityInfo eInfo1 = new EntityInfoImpl(str1, pos1);
        final EntityInfo eInfo2 = new EntityInfoImpl(str1, pos1);
        assertNotEquals(eInfo1, eInfo2);
        assertFalse(eInfo1.equals(eInfo2));
        final EntityInfo eInfo3 = eInfo1;
        assertEquals(eInfo1, eInfo3);
        assertTrue(eInfo1.equals(eInfo3));
    }

    @Test
    public void testEqualsPlantInfo() {
        final PlantInfo eInfo1 = new PlantInfoImpl(str1, cost1);
        final PlantInfo eInfo2 = new PlantInfoImpl(str1, cost1);
        assertNotEquals(eInfo1, eInfo2);
        assertFalse(eInfo1.equals(eInfo2));
        final PlantInfo eInfo3 = eInfo1;
        assertEquals(eInfo1, eInfo3);
        assertTrue(eInfo1.equals(eInfo3));
    }

    @Test
    public void testMoveEntityInfo() {
        final Zombie basic = ZOMBIE_FACTORY.basic(pos1);
        final EntityInfo e1 = basic.getEntityInfo();
        assertEquals(e1, basic.getEntityInfo());
        basic.move();
        assertEquals(e1, basic.getEntityInfo()); //when moved, entityInfo must remain equal
        assertEquals(e1.getPosition(), basic.getPosition()); //but also must update its position field
    }
}

package flowerforce.model.entities;

import javafx.geometry.Point2D;

public class ZombieFactoryImpl implements ZombieFactory {

    private static final double BASIC_HEALTH = 1.0;
    private static final double BASIC_DAMAGE = 0.1;
    private static final int BASIC_DELTA = 5;

    @Override
    public Zombie basic(Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position);
    }

    @Override
    public Zombie conehead(Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH*3, position);
    }

    @Override
    public Zombie buckethead(Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE*2, BASIC_HEALTH*10, position);
    }

    @Override
    public Zombie running(Point2D position) {
        return new ZombieImpl(BASIC_DELTA*2, BASIC_DAMAGE, BASIC_HEALTH, position);
    }

    @Override
    public Zombie quarterback(Point2D position) {
        return new ZombieImpl(BASIC_DELTA*2, BASIC_DAMAGE, BASIC_HEALTH*13, position);
    }
    
}

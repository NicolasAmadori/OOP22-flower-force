package flowerforce.model.entities;

import javafx.geometry.Point2D;

public class ZombieFactoryImpl implements ZombieFactory {

    private static final double BASIC_HEALTH = 1.0;
    private static final double BASIC_DAMAGE = 0.15;
    private static final int BASIC_DELTA = 5;
    private static final double CONE_HEALTH = 2.0;
    private static final double BUCKET_HEALTH = 9.0;
    private static final double HELMET_HEALTH = 12.0;
    private static final int RUNNING_DELTA = 10;

    @Override
    public Zombie basic(Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position);
    }

    @Override
    public Zombie conehead(Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH + CONE_HEALTH, position);
    }

    @Override
    public Zombie buckethead(Point2D position) {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH + BUCKET_HEALTH, position);
    }

    @Override
    public Zombie running(Point2D position) {
        return new ZombieImpl(RUNNING_DELTA, BASIC_DAMAGE, BASIC_HEALTH, position);
    }

    @Override
    public Zombie quarterback(Point2D position) {
        return new ZombieImpl(RUNNING_DELTA, BASIC_DAMAGE, BASIC_HEALTH + HELMET_HEALTH, position);
    }
    
}

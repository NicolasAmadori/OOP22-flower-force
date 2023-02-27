package flowerforce.model.entities;

public class ZombieFactoryImpl implements ZombieFactory {

    private static final double BASIC_HEALTH = 1.0;
    private static final double BASIC_DAMAGE = 0.1;
    private static final int BASIC_DELTA = 5;

    @Override
    public Zombie basic() {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH);
    }

    @Override
    public Zombie withTrafficCone() {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE, BASIC_HEALTH*2);
    }

    @Override
    public Zombie withMetalBucket() {
        return new ZombieImpl(BASIC_DELTA, BASIC_DAMAGE*2, BASIC_HEALTH*3);
    }

    @Override
    public Zombie running() {
        return new ZombieImpl(BASIC_DELTA*2, BASIC_DAMAGE, BASIC_HEALTH);
    }
    
}

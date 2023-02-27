package flowerforce.model.entities;

public interface LivingEntity extends Entity{

    double getHealth();

    void receiveDamage(double damage);
    
}

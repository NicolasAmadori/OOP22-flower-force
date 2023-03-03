package flowerforce.model.entities;

public interface Zombie extends MovingEntity, LivingEntity{
    void freeze();

    void warmUp();
}

package flowerforce.model.entities;

public interface Zombie extends ActiveEntity, LivingEntity {

    void freeze();

    void warmUp();

    boolean bite();
    
    //eat() e stopEating() ?
}

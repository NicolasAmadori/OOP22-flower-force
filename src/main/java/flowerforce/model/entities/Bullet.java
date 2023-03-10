package flowerforce.model.entities;

/**
 * Models a bullet, shot by some plants against zombies.
 */
public interface Bullet extends MovingEntity {

    /**
     * Called to hit a zombie.
     * @param zombie the zombie to hit
     */
    void hit(Zombie zombie);

}

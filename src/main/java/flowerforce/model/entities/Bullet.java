package flowerforce.model.entities;

/**
 * Models a bullet, shot by some plants against zombies.
 */
public interface Bullet extends ActiveEntity {

    /**
     * Called to hit a zombie.
     * @param zombie the zombie to hit
     */
    void hit(Zombie zombie);

}

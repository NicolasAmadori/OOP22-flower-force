package flowerforce.model.entities.bullets;

import flowerforce.model.entities.MovingEntity;
import flowerforce.model.entities.Zombie;

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

package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Bullets;

/**
 * Models a bullet, shot by some plants against zombies.
 */
public interface Bullet extends MovingEntity {

    /**
     * Called to hit a zombie.
     * @param zombie the zombie to hit
     */
    void hit(Zombie zombie);

    Bullets getBulletType();

}

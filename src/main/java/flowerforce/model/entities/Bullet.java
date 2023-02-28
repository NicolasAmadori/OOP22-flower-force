package flowerforce.model.entities;

/**
 * Models a bullet, shot by some plants against zombies.
 */
public interface Bullet extends ActiveEntity {

    /**
     * 
     * @return true if the bullet freezes the opponent
     */
    boolean isFreezeBullet();

    /**
     * 
     * @return true if the bullet warms up the opponent
     */
    boolean isFireBullet();

}

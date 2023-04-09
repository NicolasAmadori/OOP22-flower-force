package flowerforce.model.entities.plants;

import java.util.Optional;

import flowerforce.model.entities.bullets.Bullet;

/**
 * Models a plant that shoots bullets.
 */
public interface ShootingPlant extends Plant {

    /**
     * 
     * @return a non-empty Optional if the next bullet has already been produced.
     */
    Optional<Bullet> nextBullet();
}

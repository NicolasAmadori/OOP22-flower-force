package flowerforce.model.entities;

import java.util.Optional;

/**
 * Models a plant that shoots bullets.
 */
public interface ShootingPlant extends LivingEntity {

    /**
     * 
     * @return a non-empty Optional if the next bullet has already been produced.
     */
    Optional<Bullet> nextBullet();
}

package flowerforce.model.entities;

import java.util.Optional;

public interface ShootingPlant extends LivingEntity{
    Optional<Bullet> nextBullet();
}

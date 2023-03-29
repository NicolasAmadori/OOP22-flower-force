package flowerforce.model.entities;

import javafx.geometry.Point2D;

public interface BulletFactory {
    
    Bullet createStandardBullet(Point2D pos);

    Bullet createFireBullet(Point2D pos);

    Bullet createSnowdBullet(Point2D pos);

}

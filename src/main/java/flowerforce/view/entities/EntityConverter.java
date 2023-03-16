package flowerforce.view.entities;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;

public interface EntityConverter {

    EntityView getEntityView(Plant p);

    EntityView getEntityView(Zombie z);

    EntityView getEntityView(Bullet b);
}

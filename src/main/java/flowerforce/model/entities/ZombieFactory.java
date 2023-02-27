package flowerforce.model.entities;


/**
 * Model a factory of zombie with defined properties
 */
public interface ZombieFactory {

    /**
     * @return a basic zombie
     */
    Zombie basic();

    /** 
     * @return a medium-resistance zombie
     */
    Zombie withTrafficCone();

    /** 
     * @return a high-resistance zombie
     */
    Zombie withMetalBucket();

    /**
     * @return a running zombie
     */
    Zombie running();
    
}

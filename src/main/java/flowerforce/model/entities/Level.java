package flowerforce.model.entities;

import java.util.List;

/**
 * It contains the information that characterizes each level of the game.
 */
public interface Level {
    /**
     * 
     * @return The number of coins that is assigned once the level is completed
     */
    int getLevelCoins();

    /**
     * 
     * @return the plants that can be used in this level
     */
    List<Integer> getPlantsId();

    /**
     * 
     * @return the zombies that can be used in this level
     */
    List<Integer> getZombiesId();

    /**
     * 
     * @return the number of row avaiable in this level
     */
    Integer getNumberOfRowAvaiable();

    /**
     * 
     * @return the number of zombies in this level
     */
    Integer getTotalZombies();
}

package flowerforce.model.game;

import flowerforce.model.entities.IdConverter;

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
    List<IdConverter.Plants> getPlantsId();

    /**
     * 
     * @return the zombies that can be used in this level
     */
    List<IdConverter.Zombies> getZombiesId();

    /**
     * 
     * @return the number of zombies in this level
     */
    Integer getTotalZombies();

    /**
     *
     * @return the ID of the level
     */
    Integer getLevelId();
}

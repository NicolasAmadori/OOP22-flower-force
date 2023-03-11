package flowerforce.view.game;

import java.util.Optional;

import javafx.scene.Scene;

public interface FlowerForceScene {

    /**
     * 
     * @return the javafx scene
     */
    Scene getScene();

    /**
     * 
     * @return the game engine if there is one in this scene, empty otherwise.
     */
    Optional<GameEngine> getGameEngine();

}

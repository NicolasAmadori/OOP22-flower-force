package flowerforce.view.game;

import flowerforce.controller.Controller;

/**
 * Models the central interface of the view.
 */
public interface FlowerForceView {

    /**
     * This method is called to set the menu scene in the application.
     */
    void menu();

    /**
     * This method is called to start an Adventure Mode game (i.e. a level).
     * @param levelId the id of the selected level
     */
    void adventureModeGame(int levelId);

    /**
     * This method is called to start a Survival mode game.
     */
    void survivalModeGame();

    /**
     * This method is called to set the how to play scene in the application.
     */
    void howToPlay();

    /**
     * This method is called to set shop scene in the application.
     */
    void shop();

    /**
     * 
     * @return the controller of the application
     */
    Controller getController();

}

package flowerforce.view.game;

import flowerforce.controller.Controller;

public interface FlowerForceView {

    /**
     * This method is called to set the menu scene in the application.
     */
    void menu();

    /**
     * This method is called to start a level.
     * @param levelId
     */
    void levelGame(int levelId);

    /**
     * This method is called to start an Adventure game.
     */
    void adventureGame();

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

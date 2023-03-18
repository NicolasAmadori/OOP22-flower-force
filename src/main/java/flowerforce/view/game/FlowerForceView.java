package flowerforce.view.game;

import flowerforce.controller.Controller;

public interface FlowerForceView {

    /**
     * This method is called to set the menu scene in the application.
     */
    void menu();

    /**
     * This method is called to set the game scene in the application.
     */
    void game(int levelId);

    /**
     * 
     * @return the controller of the application
     */
    Controller getController();

}

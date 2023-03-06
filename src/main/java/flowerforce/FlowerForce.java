package flowerforce;

import flowerforce.view.MyMenuGUIApp;

import static javafx.application.Application.launch;

/**
 * Program's main class.
 */
public final class FlowerForce {

    private FlowerForce() {
    }

    /**
     * Program's entry point.
     * @param args
     */
    public static void main(final String[] args) {
        launch(MyMenuGUIApp.class, args);
    }


}

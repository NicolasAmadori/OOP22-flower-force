package flowerforce;

import static javafx.application.Application.launch;

import flowerforce.view.game.GameViewImpl;

public final class FlowerForce {

    public final static void main(final String[] args) {
        launch(GameViewImpl.class, args);
    }


}

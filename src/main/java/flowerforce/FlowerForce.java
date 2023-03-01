package flowerforce;

import flowerforce.view.GameViewImpl;

import static javafx.application.Application.launch;

public final class FlowerForce{

    public final static void main(final String[] args) {
        launch(GameViewImpl.class, args);
    }


}

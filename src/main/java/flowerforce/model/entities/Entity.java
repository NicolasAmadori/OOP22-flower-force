package flowerforce.model.entities;

import javafx.geometry.Point2D;

public interface Entity {

    Point2D getPosition();

    boolean isOver();

    void update();

    void setPosition(Point2D position);

}

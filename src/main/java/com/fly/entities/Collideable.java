package com.fly.entities;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public interface Collideable {
    default boolean contains(Collideable collideable) {
        return getRectangle().contains(collideable.getCenter());
    }

    default boolean intersect(Rectangle2D rectangle2D) {
        return getRectangle().intersects(rectangle2D);
    }

    void onCollided(Collideable collideable);

    Point2D getCenter();

    Rectangle2D getRectangle();
}

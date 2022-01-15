package com.fly.entities;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public interface CollideAble {
    default boolean contains(CollideAble collideable) {
        return getRectangle().contains(collideable.getCenter());
    }

    default boolean intersect(Rectangle2D rectangle2D) {
        return getRectangle().intersects(rectangle2D);
    }

    void onCollided(CollideAble collideable) throws Exception;

    Point2D getCenter();

    Rectangle2D getRectangle();
}

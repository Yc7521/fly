package com.fly.entities.env.wall;

import com.fly.entities.CollideAble;
import com.fly.entities.MoveAble;
import com.fly.math.Interp;
import javafx.geometry.Point2D;

public interface RepulsionAble extends CollideAble {

    default void onCollided(CollideAble collideable) throws Exception {
        if (collideable instanceof MoveAble moveable) {
            final Point2D objCenter = collideable.getCenter();
            final Point2D center = this.getCenter();
            final double radians = Math.toRadians(moveable.getDirection());
            final float speed = moveable.getSpeed();
            if (speed == 0) return;
            final float _dx = (float) (speed * Math.sin(radians)), _dy = (float) (speed * Math.cos(radians));
            final float r = getRadius();
            final float dx, dy;
            switch (getStyle()) {
                case CIRCLE -> {
                    final float v = (float) Math.abs(objCenter.distance(center) / r);
                    if (v > 1) return;
                    final float multi = Interp.linear.apply(-2f, 0.5f, v);
                    dx = getDx(objCenter, center, _dx, multi);
                    dy = getDy(objCenter, center, _dy, multi);
                }
                case VERTICAL -> {
                    final float v = (float) Math.abs((moveable.getX() - center.getX()) / r);
                    if (v > 1) return;
                    final float multi = Interp.linear.apply(-2f, 0.5f, v);
                    dx = getDx(objCenter, center, _dx, multi);
                    dy = _dy;
                }
                case HORIZONTAL -> {
                    final float v = (float) Math.abs((moveable.getY() - center.getY()) / r);
                    if (v > 1) return;
                    final float multi = Interp.linear.apply(-2f, 0.5f, v);
                    dx = _dx;
                    dy = getDy(objCenter, center, _dy, multi);
                }
                default -> {
                    dx = _dx;
                    dy = _dy;
                }
            }
            final Point2D speedV = new Point2D(dx, dy);
            moveable.setDirection(90 - (float) Math.toDegrees(Math.atan2(dy, dx)));
            moveable.setSpeed((float) speedV.magnitude());
        }
    }

    private float getDy(Point2D objCenter, Point2D center, float _dy, float multi) {
        final float dy;
        if (objCenter.getY() < center.getY() && _dy > 0) {
            dy = _dy * multi;
        } else if (objCenter.getY() > center.getY() && _dy < 0) {
            dy = _dy * multi;
        } else {
            dy = _dy;
        }
        return dy;
    }

    private float getDx(Point2D objCenter, Point2D center, float _dx, float multi) {
        final float dx;
        if (objCenter.getX() < center.getX() && _dx > 0) {
            dx = _dx * multi;
        } else if (objCenter.getX() > center.getX() && _dx < 0) {
            dx = _dx * multi;
        } else {
            dx = _dx;
        }
        return dx;
    }

    Style getStyle();

    float getRadius();

    enum Style {
        CIRCLE, VERTICAL, HORIZONTAL
    }
}

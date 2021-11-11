package com.fly.entities.unit.controller;

import com.fly.entities.unit.Units;
import javafx.geometry.Point2D;

import static com.fly.game.Game.invertY;

public interface IController {
    /***
     * do move
     * @param dirPressed [up, down, left, right]
     */
    void onMove(boolean[] dirPressed);

    /**
     * get move relative position
     *
     * @param dirPressed [up, down, left, right]
     * @return [y, x]
     */
    default int[] getMoveRelPos(boolean[] dirPressed) {
        int[] pos = {1, 1};
        pos[0] -= dirPressed[0] ? 1 : 0;
        pos[0] += dirPressed[1] ? 1 : 0;
        pos[1] -= dirPressed[2] ? 1 : 0;
        pos[1] += dirPressed[3] ? 1 : 0;
        if (invertY)
            pos[0] = 2 - pos[0];
        return pos;
    }

    /**
     * just rotate to this target
     *
     * @param x 坐标
     * @param y 坐标
     */
    void onAim(int x, int y);

    void onAcc(boolean on);

    Units getUnits();

    default void onShot() {
        getUnits().shot();
    }

    default void onShot(Point2D pos) {
        final Point2D xAxis = new Point2D(0, -1);
        final Point2D center = getUnits().getCenter();
        final Point2D diff = pos.subtract(center);
        final float dir = 90 - (float) Math.toDegrees(Math.atan2(diff.getY(), diff.getX()));
        getUnits().shot(dir);
    }
}

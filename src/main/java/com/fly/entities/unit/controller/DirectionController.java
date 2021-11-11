package com.fly.entities.unit.controller;

import com.fly.entities.unit.Units;

// TODO: fix this use IController
public class DirectionController implements IController {
    private static final float[][] mapping = {{315, 0, 45}, {270, 0, 90}, {225, 180, 135},};
    private final Units units;

    public DirectionController(Units units) {
        this.units = units;
    }

    @Override
    public void onMove(boolean[] dirPressed) {
        int[] pos = getMoveRelPos(dirPressed);

        if (pos[1] != 1 || pos[0] != 1) {
            aim(mapping[pos[0]][pos[1]]);
        } else {
            units.decelerate(0.5f);
        }
    }

    private void aim(float direction) {
        float diff = direction - units.getDirection();
        float diffAbs = Math.abs(diff);
        if (diffAbs > 180)
            units.rotate(-diff);
        else if (diffAbs > .001)
            units.rotate(diff);

        if (diffAbs < 10) {
            units.accelerate(1 - diffAbs / 10);
        } else {
            units.decelerate(0.1f);
        }
    }

    @Override
    public void onAim(int x, int y) {

    }

    @Override
    public void onAcc(boolean on) {

    }

    @Override
    public Units getUnits() {
        return units;
    }

}

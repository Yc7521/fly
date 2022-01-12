package com.fly.entities.unit.controller;

import com.fly.entities.unit.Units;

// TODO: fix this use IController
public class RotateController implements IController {
    private final Units units;
    private boolean accelerate = false;

    public RotateController(Units units) {
        this.units = units;
    }

    @Override
    public void onMove(boolean[] dirPressed) {
        int[] pos = getMoveRelPos(dirPressed);
        pos[1] = 2 - pos[1];

        switch (pos[0]) {
            case 0 -> {
                if (units.getSpeed() > 0) {
                    units.decelerate();
                } else {
                    units.back();
                }
            }
            case 2 -> {
                if (units.getSpeed() < 0) {
                    units.decelerate();
                } else {
                    units.accelerate(accelerate);
                }
            }
        }
        switch (pos[1]) {
            case 0 -> units.rotate(-90);
            case 2 -> units.rotate(90);
        }
    }

    @Override
    public void onAim(int x, int y) {

    }

    @Override
    public void onAcc(boolean on) {
        accelerate = on;
    }

    @Override
    public Units getUnits() {
        return units;
    }
}

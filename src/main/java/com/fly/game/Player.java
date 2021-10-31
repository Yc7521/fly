package com.fly.game;

import com.fly.entities.Units;

import static com.fly.game.Game.invertY;

public class Player {
    private static final float[][] mapping = {{315, 0, 45}, {270, 0, 90}, {225, 180, 135},};
    private Units units = new Units(50, 50) {{
        this.style = Style.triangle;
    }};

    // return [y, x]
    private static int[] getMoveArg(boolean[] dirPressed) {
        int[] pos = {1, 1};
        pos[0] -= dirPressed[0] ? 1 : 0;
        pos[0] += dirPressed[1] ? 1 : 0;
        pos[1] -= dirPressed[2] ? 1 : 0;
        pos[1] += dirPressed[3] ? 1 : 0;
        if (invertY)
            pos[0] = 2 - pos[0];
        return pos;
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

    public void moveByDir(boolean[] dirPressed) {
        int[] pos = getMoveArg(dirPressed);

        if (pos[1] != 1 || pos[0] != 1) {
            aim(mapping[pos[0]][pos[1]]);
        } else {
            units.decelerate(0.5f);
        }
        units.move();
    }

    public void moveByRotate(boolean[] dirPressed) {
        int[] pos = getMoveArg(dirPressed);
        pos[1] = 2 - pos[1];

        switch (pos[0]) {
            case 0 -> units.decelerate();
            case 2 -> units.accelerate();
        }
        switch (pos[1]) {
            case 0 -> units.rotate(-90);
            case 2 -> units.rotate(90);
        }
        units.move();
    }

    public void move(boolean[] dirPressed) {
        moveByRotate(dirPressed);
    }

    public Units getUnits() {
        return units;
    }
}

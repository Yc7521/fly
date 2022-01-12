package com.fly.entities.env.wall;

import com.fly.entities.Collideable;
import com.fly.entities.effect.MoveEffect;
import com.fly.entities.unit.Units;
import com.fly.game.Game;
import javafx.scene.paint.Color;

public class TpWall extends WallBase {
    float toX, toY;// 传送位置

    public TpWall(float x, float y, float width, float height, float toX, float toY) {
        super(x, y, width, height);
        color = Color.rgb(0x40, 0xF9, 0xFF);
        this.toX = toX;
        this.toY = toY;
    }

    public TpWall(float x, float y, float width, float height, float toX, float toY, Color color) {
        super(x, y, width, height, color);
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public void onCollided(Collideable collideable) throws Exception {
        if (collideable instanceof Units units) {
            System.out.println("传送了");
            WallBase.backUnit(units);
            Game.addEffect(new MoveEffect(
                    units,
                    toX < 0 ? units.getX() : toX,
                    toY < 0 ? units.getY() : toY,
                    1.5f,
                    20));
        }
    }
}

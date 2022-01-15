package com.fly.entities.env.wall;

import com.fly.entities.CollideAble;
import com.fly.entities.effect.MoveEffect;
import com.fly.entities.unit.Units;
import javafx.scene.paint.Color;

public class TpWall extends WallBase {
    float toX, toY;// 传送位置

    public TpWall(float x,
                  float y,
                  float width,
                  float height,
                  float toX,
                  float toY,
                  Style style) {
        this(x, y, width, height, toX, toY, style, Color.rgb(0x40, 0xF9, 0xFF));
    }

    public TpWall(float x,
                  float y,
                  float width,
                  float height,
                  float toX,
                  float toY,
                  Style style,
                  Color color) {
        super(x, y, width, height, style, color);
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public void onCollided(CollideAble collideable) throws Exception {
//        super.onCollided(collideAble);
        if (collideable instanceof Units units) {
            units.addEffect(new MoveEffect(units, toX < 0 ? units.getX() : toX, toY < 0 ? units.getY() : toY, 1.5f, 20));
        }
    }
}

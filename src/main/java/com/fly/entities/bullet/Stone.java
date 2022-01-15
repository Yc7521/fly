package com.fly.entities.bullet;

import com.fly.entities.CollideAble;
import com.fly.entities.effect.BoomEffect;
import com.fly.entities.env.wall.WallBase;
import com.fly.entities.unit.Units;
import com.fly.game.Game;
import javafx.scene.paint.Color;

public class Stone extends BulletBase {
    {
        radius = 30;
        color = Color.RED;
        alive = 3f;
        damage = 10;
    }

    public Stone(float x, float y, float speed, float direction) {
        super(null, x, y, speed, direction);
    }

    public Stone(Units owner, float x, float y, float speed, float direction) {
        super(owner, x, y, speed, direction);
    }

    @Override
    public void onCollided(CollideAble collideable) throws Exception {
        super.onCollided(collideable);
        if (collideable instanceof BulletBase) {
            alive = -1;
            Game.player.addScore(10);
        } else if (collideable instanceof WallBase wall) {
            alive = -1;
            return;
        }
        Game.addEffect(new BoomEffect(x, y, 1f, 3));
    }
}

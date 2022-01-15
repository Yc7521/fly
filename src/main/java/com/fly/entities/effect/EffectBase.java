package com.fly.entities.effect;

import com.fly.entities.RenderAble;
import com.fly.game.Game;
import javafx.scene.canvas.GraphicsContext;

public class EffectBase implements RenderAble {
    private float tick = 0;
    private boolean end = false;

    @Override
    public void render(GraphicsContext g) {
        tick++;
    }

    public float getTime() {
        return tick / Game.fps;
    }

    public boolean isEnd() {
        return end;
    }

    protected void setEnd(boolean end) {
        this.end = end;
    }

    protected void setEnd() {
        this.end = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

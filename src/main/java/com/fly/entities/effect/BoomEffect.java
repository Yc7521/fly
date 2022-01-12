package com.fly.entities.effect;

import com.fly.math.Interp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BoomEffect extends EffectBase {
    float x, y; // boom src
    float time; // time needed spend
    float radius; // effect radius

    public BoomEffect(float x, float y, float time, float radius) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.radius = radius;
    }

    @Override
    public void render(GraphicsContext g) {
        final float time = getTime();
        if (time >= this.time) {
            setEnd();
        }
        super.render(g);
        final float r = Interp.linear.apply(0, radius, time / this.time);
        Paint stroke = g.getStroke();
        g.setStroke(Color.RED);
        g.strokeOval(x - r, y - r, 2 * r, 2 * r);
        g.setStroke(stroke);
    }
}
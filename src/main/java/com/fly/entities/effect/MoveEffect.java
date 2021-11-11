package com.fly.entities.effect;

import com.fly.entities.unit.Units;
import com.fly.math.Interp;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class MoveEffect extends EffectBase {
    Units target; // target unit
    float toX, toY; // pos which will move to
    float time; // time needed spend
    float radius; // effect radius

    public MoveEffect(Units target, float toX, float toY, float time, float radius) {
        this.target = target;
        this.toX = toX;
        this.toY = toY;
        this.time = time;
        this.radius = radius;
    }

    @Override
    public void render(GraphicsContext g) {
        target.setSpeed(0);
        final float time = getTime();
        if (time >= this.time) {
            setEnd();
            target.moveTo(toX, toY);
        }
        super.render(g);
        final float r = Interp.linear.apply(radius, 0, time / this.time);
        final Point2D center = target.getCenter();
        g.strokeOval(center.getX() - r, center.getY() - r, 2 * r, 2 * r);
    }
}

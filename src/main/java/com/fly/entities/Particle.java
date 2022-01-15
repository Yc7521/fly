package com.fly.entities;

import com.fly.game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Particle extends MoveAble implements RenderAble {
    protected float radius = 2; // 半径
    protected Color color = Color.rgb(255, 0, 0); // 颜色
    protected float alive = .25f; // 生存时间

    public Particle(float x, float y, float speed, float direction) {
        super(x, y, speed, direction);
    }

    public void setColor(int r, int g, int b) {
        color = Color.rgb(r, g, b);
    }

    public float getAlive() {
        return alive;
    }

    @Override
    public void render(GraphicsContext g) {
        alive -= 1 / Game.fps;
        Paint stroke = g.getFill();
        g.setFill(color);
        g.fillOval(x - radius, y - radius, radius, radius);
        g.setFill(stroke);
    }
}

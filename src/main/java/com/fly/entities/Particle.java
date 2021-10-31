package com.fly.entities;

import com.fly.game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Particle extends Moveable implements Renderable {
    protected float r = 2; // 半径
    protected Color color = Color.rgb(255, 0, 0); // 颜色
    protected float alive = .5f; // 生存时间

    public Particle(float x, float y, float speed, float direction) {
        super(x, y, speed, direction);
    }

    public void setColor(int r, int g, int b) {
        color = Color.rgb(r, g, b);
    }

    public float getAlive() {
        alive -= 1 / Game.fps;
        return alive;
    }

    @Override
    public void render(GraphicsContext g) {
        Paint stroke = g.getFill();
        g.setFill(color);
        g.fillOval(x - r, y - r, r, r);
        g.setFill(stroke);
    }
}

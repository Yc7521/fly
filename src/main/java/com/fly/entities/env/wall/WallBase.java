package com.fly.entities.env.wall;

import com.fly.entities.Collideable;
import com.fly.entities.Renderable;
import com.fly.entities.unit.Units;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class WallBase implements Renderable, Collideable {
    protected float x, y; // 左上位置
    protected float width, height; // 大小
    protected Color color = Color.rgb(64, 64, 64); // 颜色

    public WallBase(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public WallBase(float x, float y, float width, float height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void render(GraphicsContext g) {
        Paint stroke = g.getFill();
        g.setFill(color);
        g.fillPolygon(new double[]{x, x + width, x + width, x},
                new double[]{y, y, y + height, y + height},
                4);
        g.setFill(stroke);
    }

    @Override
    public void onCollided(Collideable collideable) {
        if (collideable instanceof Units units) {
            System.out.println("撞墙了");
            backUnit(units);
            units.setSpeed(0);
        }
    }

    protected static void backUnit(Units units) {
        final float maxSpeed = (units.getSpeed() < 0 ? 1 : -1) * units.getMaxSpeed();
        double radians = Math.toRadians(units.getDirection());
        units.move((float) (maxSpeed * Math.sin(radians)),
                (float) (maxSpeed * Math.cos(radians)));
    }

    @Override
    public Point2D getCenter() {
        return new Point2D(x, y);
    }

    @Override
    public Rectangle2D getRectangle() {
        return new Rectangle2D(x, y, width, height);
    }
}

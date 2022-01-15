package com.fly.entities.env.wall;

import com.fly.entities.CollideAble;
import com.fly.entities.RenderAble;
import com.fly.entities.unit.Units;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class WallBase implements RenderAble, CollideAble, RepulsionAble {
    protected Style style;
    protected float x, y; // 左上位置
    protected float width, height, radius; // 大小
    protected Color color; // 颜色

    public WallBase(float x, float y, float width, float height, Style style) {
        this(x, y, width, height, style, Color.rgb(64, 64, 64));
    }

    public WallBase(float x,
                    float y,
                    float width,
                    float height,
                    Style style,
                    Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.style = style;
        this.radius = switch (style) {
            case CIRCLE -> Math.max(width, height) * 2;
            case HORIZONTAL -> height * 2;
            case VERTICAL -> width * 2;
        };
    }

    private static void __backUnit__old(Units units) {
        final float speed = units.getSpeed();
        if (speed == 0) return;
        final float maxSpeed = (speed < 0 ? 1 : -1) * units.getMaxSpeed();
        double radians = Math.toRadians(units.getDirection());
        units.move((float) (maxSpeed * Math.sin(radians)), (float) (maxSpeed * Math.cos(radians)));
    }

    protected void backUnit(Units units) {
//        __backUnit__old(units);
    }

    @Override
    public void render(GraphicsContext g) {
        Paint stroke = g.getFill();
        g.setFill(color);
        g.fillPolygon(new double[]{x, x + width, x + width, x}, new double[]{y, y, y + height, y + height}, 4);
        g.setFill(stroke);
    }

    @Override
    public void onCollided(CollideAble collideable) throws Exception {
        RepulsionAble.super.onCollided(collideable);
        if (collideable instanceof Units units) {
//            System.out.println("撞墙了");
//            backUnit(units);
//            units.setSpeed(0);
        }
    }

    @Override
    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public Point2D getCenter() {
        return new Point2D(x + width / 2, y + height / 2);
    }

    @Override
    public Rectangle2D getRectangle() {
        return new Rectangle2D(x, y, width, height);
    }
}

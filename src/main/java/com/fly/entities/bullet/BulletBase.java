package com.fly.entities.bullet;

import com.fly.entities.Collideable;
import com.fly.entities.Particle;
import com.fly.entities.unit.Units;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

public class BulletBase extends Particle implements Collideable {
    private int damage;

    public BulletBase(float x, float y, float speed, float direction) {
        super(x, y, speed, direction);
        radius = 10;
        alive = 0.4f;
        color = Color.AQUA;
    }

    @Override
    public void onCollided(Collideable collideable) {
        if (collideable instanceof Units units) {
            System.out.println("中弹了");
            units.damage(getDamage());
        }
        alive = -1;
    }

    @Override
    public Point2D getCenter() {
        return new Point2D(x, y);
    }

    @Override
    public Rectangle2D getRectangle() {
        return new Rectangle2D(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
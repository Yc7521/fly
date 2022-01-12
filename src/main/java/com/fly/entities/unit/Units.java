package com.fly.entities.unit;

import com.fly.entities.Collideable;
import com.fly.entities.Moveable;
import com.fly.entities.Particle;
import com.fly.entities.bullet.BulletBase;
import com.fly.entities.env.wall.WallBase;
import com.fly.game.Game;
import com.fly.math.Interp;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

public class Units extends Moveable implements Collideable {
    protected final int radius = 10; // 半径
    public LinkedList<Particle> particles = new LinkedList<>();
    protected float maxSpeed = 3, accSpeed = 5; // 速度
    protected float acceleration = 2; // 加速
    protected float rotateSpeed = 180; // 旋转速度 0-360
    protected Style style = Style.triangle; // 样式
    protected float shotSpeed = 3; // shot times per second
    protected int hp = 100, maxHp = 100;
    private int tick;

    public Units() {
    }

    public Units(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static double[][] matrixDot(double[][] a, double[][] b) {
        //当a的列数与矩阵b的行数不相等时，不能进行点乘，返回null
        if (a[0].length != b.length)
            return null;
        //c矩阵的行数y，与列数x
        int y = a.length;
        int x = b[0].length;
        double[][] c = new double[y][x];
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                for (int k = 0; k < b.length; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    private static double[][] rotate(double[][] point, float dir) {
        double radians = Math.toRadians(dir);
        double s = Math.sin(radians);
        double c = Math.cos(radians);
        double[][] r = {{c, -s}, {s, c}};
        return matrixDot(r, point);
    }

    private static void add(double[] point, float val) {
        for (int j = 0; j < point.length; j++) {
            point[j] += val;
        }
    }

    public void rotate(float direction) {
        if (direction > 0) {
            this.direction += Math.min(rotateSpeed / Game.fps, direction);
            addParticle(180 - 30 + this.direction);
        } else {
            this.direction += Math.max(-rotateSpeed / Game.fps, direction);
            addParticle(180 + 30 + this.direction);
        }
        if (this.direction < 0) this.direction += 360;
        if (this.direction >= 360) this.direction -= 360;
    }

    public void rotateTo(float direction) {
        rotate(direction - this.direction);
    }

    public void accelerate(boolean shift) {
        accelerate(1, shift);
    }

    public void accelerate(float per, boolean shift) {
        speed += Interp.linear.apply(0, acceleration / Game.fps, per);
        final float max = shift ? accSpeed : maxSpeed;
        if (speed > max) speed = max;
        addParticle(direction + 180);
    }

    public void decelerate() {
        decelerate(1);
    }

    public void decelerate(float per) {
        if (speed < 0) {
            speed += Interp.linear.apply(0, acceleration / Game.fps, per);
        } else if (speed > 0) {
            speed -= Interp.linear.apply(0, acceleration / Game.fps, per);
        }
    }

    public void back() {
        back(1);
    }

    public void back(float per) {
        speed -= Interp.linear.apply(0, acceleration / Game.fps, per);
        if (speed < -maxSpeed) speed = -maxSpeed;
        addParticle(direction + 180, true);
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getRotateSpeed() {
        return rotateSpeed;
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public void move() {
        super.move();
        particles.removeIf(particle -> particle.getAlive() < 0);
        for (Particle particle : particles) {
            particle.move();
        }
    }

    @Override
    public void render(GraphicsContext g) {
        tick++;
        switch (style) {
            case square -> {
                double[][] p = {
                        {-radius, +radius, -radius, +radius},
                        {-radius, -radius, +radius, +radius}};
                p = rotate(p, -direction);
                add(p[0], x);
                add(p[1], y);
                g.strokePolygon(p[0], p[1], 4);
            }
            case circle -> {
                double radians = Math.toRadians(direction);
                float dx = (float) Math.sin(radians);
                float dy = (float) Math.cos(radians);
                g.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
                g.fillOval(x + radius * dx - 2, y + radius * dy - 2, 2, 2);
            }
            case triangle -> {
                double[][] p = {
                        {0, -radius, +radius},
                        {+radius, -radius, -radius}};
                p = rotate(p, -direction);
                add(p[0], x);
                add(p[1], y);
                g.strokePolygon(p[0], p[1], 3);
            }
        }
        for (Particle particle : particles) {
            particle.render(g);
        }
    }

    private void addParticle(float dir) {
        addParticle(dir, false);
    }

    private void addParticle(float dir, boolean reverse) {
        final int shake = 20;
        final float minSpeed = (reverse ? 0.5f : 1f) * acceleration;
        final float maxSpeed = 1.5f * acceleration;
        double radians = Math.toRadians(dir);
        float dx = (float) Math.sin(radians);
        float dy = (float) Math.cos(radians);
        for (int i = 0; i < 1; i++) {
            particles.add(new Particle(
                    x + radius * dx,
                    y + radius * dy,
                    (reverse ? -1 : 1) * Interp.linear.apply(minSpeed, maxSpeed, (float) Math.random()),
                    Interp.linear.apply(dir - shake, dir + shake, (float) Math.random())
            ));
        }
    }

    private float getShotDelay() {
        return Game.fps / shotSpeed;
    }

    private void addBullet(float dir) {
        if (tick < getShotDelay()) return;
        tick = 0;
        final int shake = 3;
        final int minSpeed = 20;
        final int maxSpeed = 30;
        double radians = Math.toRadians(dir);
        float dx = (float) Math.sin(radians);
        float dy = (float) Math.cos(radians);
        try {
            Game.addBullets(new BulletBase(
                    this,
                    x + radius * dx,
                    y + radius * dy,
                    Interp.linear.apply(minSpeed, maxSpeed, (float) Math.random()),
                    Interp.linear.apply(dir - shake, dir + shake, (float) Math.random())
            ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shot() {
        addBullet(this.direction);
    }

    /**
     * @param dir abs direction
     */
    public void shot(float dir) {
        addBullet(dir);
    }

    @Override
    public void onCollided(Collideable collideable) {
    }

    public void damage(int hp) {
        this.hp -= hp;
        if (this.hp < 0) this.hp = 0;
        if (this.hp > this.maxHp) this.hp = this.maxHp;
    }

    @Override
    public Point2D getCenter() {
        return new Point2D(x, y);
    }

    @Override
    public Rectangle2D getRectangle() {
        return new Rectangle2D(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    public enum Style {
        square, circle, triangle
    }
}

package com.fly.entities;

import com.fly.game.Game;
import com.fly.math.Interp;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

public class Units extends Moveable implements Renderable {
    public LinkedList<Particle> particles = new LinkedList<>();
    protected float maxSpeed = 5; // 速度
    protected float acceleration = 3; // 加速
    protected float rotateSpeed = 180; // 旋转速度 0-360
    protected Style style = Style.triangle; // 样式

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

    public void accelerate() {
        accelerate(1);
    }

    public void accelerate(float per) {
        speed += Interp.linear.apply(0, acceleration / Game.fps, per);
        if (speed > maxSpeed) speed = maxSpeed;
        addParticle(direction + 180);
    }

    public void decelerate() {
        decelerate(1);
    }

    public void decelerate(float per) {
        speed -= Interp.linear.apply(0, acceleration / Game.fps, per);
        if (speed < 0) speed = 0;
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
        switch (style) {
            case square -> {
                double[][] p = {
                        {-10, +10, -10, +10},
                        {-10, -10, +10, +10}};
                p = rotate(p, -direction);
                add(p[0], x);
                add(p[1], y);
                g.strokePolygon(p[0], p[1], 4);
            }
            case circle -> {
                double radians = Math.toRadians(direction);
                float dx = (float) Math.sin(radians);
                float dy = (float) Math.cos(radians);
                g.strokeOval(x - 10, y - 10, 20, 20);
                g.fillOval(x + 10 * dx - 2, y + 10 * dy - 2, 2, 2);
            }
            case triangle -> {
                double[][] p = {
                        {0, -10, +10},
                        {+10, -10, -10}};
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
        double radians = Math.toRadians(dir);
        float dx = (float) Math.sin(radians);
        float dy = (float) Math.cos(radians);
        for (int i = 0; i < 5; i++) {
            particles.add(new Particle(
                    x + 10 * dx,
                    y + 10 * dy,
                    Interp.linear.apply(5, 9, (float) Math.random()),
                    Interp.linear.apply(dir - 20, dir + 20, (float) Math.random())
            ));
        }
    }

    public enum Style {
        square, circle, triangle
    }
}

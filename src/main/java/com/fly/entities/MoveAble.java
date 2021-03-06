package com.fly.entities;

public abstract class MoveAble implements RenderAble {
    protected float x, y; // 位置
    protected float speed = 0;
    protected float direction = 0; // 方向 0-360 0:down;180:up

    public MoveAble() {
    }

    public MoveAble(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MoveAble(float x, float y, float speed, float direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
    }

    public void move() {
        double radians = Math.toRadians(direction);
        move((float) (speed * Math.sin(radians)),
                (float) (speed * Math.cos(radians)));
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isMoving() {
        return Math.abs(speed) > 0.000001f;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }
}

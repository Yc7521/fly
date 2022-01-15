package com.fly.entities.util;

import com.fly.entities.RenderAble;
import javafx.scene.canvas.GraphicsContext;

public class Label implements RenderAble {
    private String text;
    private float x, y;

    public Label() {
    }

    public Label(String text) {
        this(text, 0, 0);
    }

    public Label(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void render(GraphicsContext g) {
        g.strokeText(text, x, y);
    }
}

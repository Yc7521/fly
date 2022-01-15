package com.fly.game;

import com.fly.entities.unit.Units;
import com.fly.entities.unit.controller.IController;
import com.fly.entities.unit.controller.RotateController;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Player {
    private int score = 0;
    private final Units units = new Units(50, 50) {
        {
            this.style = Style.triangle;
        }

        @Override
        public void render(GraphicsContext g) {
            super.render(g);
            if (hp <= 0) {
                Game.renderTask.cancel(true);
                System.out.println("Game Over");
                System.exit(0);
            }
        }
    };
    private final IController controller = new RotateController(units);

    public void move(boolean[] dirPressed) {
        controller.onMove(dirPressed);
    }

    public void shot() {
        controller.onShot();
    }

    public void shot(Point2D pos) {
        controller.onShot(pos);
    }

    public Units getUnits() {
        return units;
    }

    public IController getController() {
        return controller;
    }

    public int getHp() {
        return units.getHp();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}

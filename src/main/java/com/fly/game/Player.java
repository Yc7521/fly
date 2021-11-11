package com.fly.game;

import com.fly.entities.unit.Units;
import com.fly.entities.unit.controller.IController;
import com.fly.entities.unit.controller.RotateController;
import javafx.geometry.Point2D;

public class Player {
    private Units units = new Units(50, 50) {{
        this.style = Style.triangle;
    }};
    private IController controller = new RotateController(units);

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
}

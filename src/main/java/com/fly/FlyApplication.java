package com.fly;

import com.fly.game.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ScheduledFuture;

import static com.fly.game.Game.*;
import static com.fly.game.Key.KeyMapping.*;

public class FlyApplication extends Application {
    // 4 dir: [up, down, left, right]
    public static final boolean[] dirPressed = new boolean[4];
    // 4 key: [fight, fight_b]
    public static final boolean[] keyPressed = new boolean[4];
    // mouse pos
    public static Point2D mouse;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlyApplication.class.getResource("fly-view.fxml"));
        Parent load = fxmlLoader.load();
        Scene scene = new Scene(load, maxX, maxY);
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (in(key, up))
                dirPressed[0] = true;
            if (in(key, down))
                dirPressed[1] = true;
            if (in(key, left))
                dirPressed[2] = true;
            if (in(key, right))
                dirPressed[3] = true;
            if (in(key, fight))
                keyPressed[0] = true;
        });
        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            if (in(key, up))
                dirPressed[0] = false;
            if (in(key, down))
                dirPressed[1] = false;
            if (in(key, left))
                dirPressed[2] = false;
            if (in(key, right))
                dirPressed[3] = false;
            if (in(key, fight))
                keyPressed[0] = false;
        });
        scene.setOnMousePressed(event -> {
            if (event.getButton() == fight_b) {
                keyPressed[1] = true;
                mouse = new Point2D(event.getX(), event.getY());
            }
        });
        scene.setOnMouseReleased(event -> {
            if (event.getButton() == fight_b) {
                keyPressed[1] = false;
            }
        });
        scene.setOnMouseDragged(event -> {
            mouse = new Point2D(event.getX(), event.getY());
        });

        canvas = (Canvas) load.lookup("#canvas");
        canvas.setWidth(maxX);
        canvas.setHeight(maxY);

        stage.setTitle("fly!");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> executor.shutdown());

        stage.show();

        Game.init();
        ScheduledFuture<?> tick = Game.tick(Game::render);
//        try {
//            Object o = tick.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}
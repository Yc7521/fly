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
    /**
     * 4 dir: [up, down, left, right]
     */
    public static final boolean[] dirPressed = new boolean[4];
    /**
     * 4 key: [shift, fight]
     */
    public static final boolean[] keyPressed = new boolean[4];
    /**
     * 4 key: [fight_b]
     */
    public static final boolean[] btnPressed = new boolean[4];
    // mouse pos
    public static Point2D mouse;

    public static void main(String[] args) {
        launch();
    }

    public static void setKey(boolean pressed,
                              boolean[] keys,
                              KeyCode key,
                              KeyCode[][] mapping) {
        for (int i = 0; i < mapping.length; i++) {
            if (in(key, mapping[i])) {
                keys[i] = pressed;
                return;
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlyApplication.class.getResource("fly-view.fxml"));
        Parent load = fxmlLoader.load();
        Scene scene = new Scene(load, maxX, maxY);
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            setKey(true, dirPressed, key, dirMapping);
            setKey(true, keyPressed, key, keyMapping);
            if (in(key, escape)) {
                System.exit(0);
            }
        });
        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            setKey(false, dirPressed, key, dirMapping);
            setKey(false, keyPressed, key, keyMapping);
        });
        scene.setOnMousePressed(event -> {
            if (event.getButton() == fight_b) {
                btnPressed[0] = true;
                mouse = new Point2D(event.getX(), event.getY());
            }
        });
        scene.setOnMouseReleased(event -> {
            if (event.getButton() == fight_b) {
                btnPressed[0] = false;
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
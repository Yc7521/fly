package com.fly.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import static com.fly.FlyApplication.dirPressed;

public class Game {
    public static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8);
    public static int maxX = 1280, maxY = 720;
    public static float fps = 60;
    public static Player player = new Player();
    public static boolean invertY = true;
    public static Canvas canvas;
    public static volatile boolean needUpdate = true;
    private static volatile AtomicInteger times = new AtomicInteger(1);
    private static volatile ReentrantLock lock = new ReentrantLock();

    public static ScheduledFuture<?> tick(Runnable runnable) {
        return executor.scheduleAtFixedRate(runnable, 0, (long) (1000 / fps), TimeUnit.MILLISECONDS);
    }

    public static void render() {
        try {
            if (lock.tryLock()) {
                int t = times.get();
                times.set(1);
                for (int i = 0; i < t; i++) {
                    player.move(dirPressed);
                }
                GraphicsContext context = canvas.getGraphicsContext2D();
                context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                player.getUnits().render(context);
                lock.unlock();
            } else {
                times.incrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

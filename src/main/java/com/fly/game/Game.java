package com.fly.game;

import com.fly.entities.bullet.BulletBase;
import com.fly.entities.bullet.Stone;
import com.fly.entities.effect.EffectBase;
import com.fly.entities.env.wall.RepulsionAble;
import com.fly.entities.env.wall.TpWall;
import com.fly.entities.env.wall.WallBase;
import com.fly.entities.unit.Units;
import com.fly.game.UI.Frame;
import com.fly.math.Interp;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import static com.fly.FlyApplication.*;

public class Game {
    //    public static volatile boolean needUpdate = true;
    private static final ArrayList<BulletBase> bullets = new ArrayList<>();
    private static final ArrayList<EffectBase> effects = new ArrayList<>();
    private static final ArrayList<WallBase> walls = new ArrayList<>();
    private static final AtomicInteger times = new AtomicInteger(1);
    private static final ReentrantLock lock = new ReentrantLock();
    public static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8);
    public static int maxX = 1280, maxY = 720;
    public static float fps = 60;
    public static Player player;
    public static boolean invertY = true;
    public static Canvas canvas;
    public static ScheduledFuture<?> renderTask;
    public static Frame frame;

    public static ScheduledFuture<?> tick(Runnable runnable) {
        return executor.scheduleAtFixedRate(runnable, 0, (long) (1000 / fps), TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> tick(Runnable runnable, int tick) {
        return executor.scheduleAtFixedRate(runnable, 0, (long) (tick * 1000L / fps), TimeUnit.MILLISECONDS);
    }

    /**
     *
     */
    public static void init() throws InterruptedException {
        bullets.clear();
        effects.clear();
        walls.clear();
        times.set(1);
        player = new Player();
        frame = new Frame();
        addWall(new WallBase(0, 0, 20, maxY, RepulsionAble.Style.VERTICAL));
        addWall(new WallBase(maxX - 20, 0, 20, maxY, RepulsionAble.Style.VERTICAL));
        addWall(new TpWall(20, 0, maxX - 40, 10, -1, maxY - 40, RepulsionAble.Style.HORIZONTAL));
        addWall(new TpWall(20, maxY - 20, maxX - 40, 20, -1, 40, RepulsionAble.Style.HORIZONTAL));
        tick(() -> {
            // make a Stone
            // take a random x in [30, maxX-30]
            final float x = Interp.linear.apply(30, maxX - 30, (float) Math.random());
            final Stone stone = new Stone(x, 40, 4, Interp.linear.apply(-20, +20, (float) Math.random()));
            try {
                Game.addBullets(stone);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 30);
    }

    /**
     * do this function per tick(1s / 60(FPS))
     */
    public static void render() {
        try {
            if (lock.tryLock()) {
                try {
                    int t = times.get();
                    times.set(1);
                    keyEventHandle(t);
                    collision();
                    render(getGraphicsContext());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                int n = times.incrementAndGet();
                if (n > 1) System.out.printf("skip %d frames%n", n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    /**
     * be sure thread safe
     */
    private static void keyEventHandle(int times) {
        for (int i = 0; i < times; i++) {
            player.getController().onAcc(keyPressed[0]);
            player.move(dirPressed);
            if (keyPressed[1]) player.shot();
            if (btnPressed[0]) player.shot(mouse);
        }
    }

    /**
     * be sure thread safe
     */
    private static void collision() throws Exception {
        final Units units = player.getUnits();
        final Rectangle2D rectangle = units.getRectangle();
        for (BulletBase bullet : bullets) {
            bullet.move();
            if (bullet.intersect(rectangle)) {
                bullet.onCollided(units);
                units.onCollided(bullet);
            }
            for (BulletBase bullet2 : bullets) {
                if (bullet == bullet2) continue;
                if (bullet.intersect(bullet2.getRectangle())) {
                    bullet.onCollided(bullet2);
                }
            }
            for (WallBase wall : walls) {
                if (bullet.intersect(wall.getRectangle())) {
                    bullet.onCollided(wall);
                    wall.onCollided(bullet);
                }
            }
        }
        for (WallBase wall : walls) {
            if (wall.intersect(rectangle)) {
                wall.onCollided(units);
                units.onCollided(wall);
            }
        }

    }

    /**
     * be sure thread safe
     */
    private static void render(GraphicsContext context) {
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        System.out.println("render");
        // render Effects
        for (EffectBase base : effects) {
            base.render(context);
        }
        effects.removeIf(EffectBase::isEnd);

        // render Walls
        for (WallBase base : walls) {
            base.render(context);
        }

        // render Bullets
        for (BulletBase base : bullets) {
            base.render(context);
        }
        bullets.removeIf(particle -> particle.getAlive() < 0);

        // render Player
        final Units units = player.getUnits();
        units.render(context);
        frame.render(context);
        units.move();
    }

    public static void addBullets(BulletBase bullet) throws InterruptedException {
        if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
            try {
                bullets.add(bullet);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void addEffect(EffectBase effect) throws InterruptedException {
        if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
            try {
                effects.add(effect);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void addWall(WallBase wall) throws InterruptedException {
        if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
            try {
                walls.add(wall);
            } finally {
                lock.unlock();
            }
        }
    }
}

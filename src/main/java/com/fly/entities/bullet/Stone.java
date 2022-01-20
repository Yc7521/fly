package com.fly.entities.bullet;

import com.fly.entities.CollideAble;
import com.fly.entities.Particle;
import com.fly.entities.effect.BoomEffect;
import com.fly.entities.env.wall.WallBase;
import com.fly.entities.unit.Units;
import com.fly.game.Game;
import com.fly.math.Interp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class Stone extends BulletBase {
    public LinkedList<Particle> particles = new LinkedList<>();

    {
        radius = 15;
        color = Color.RED;
        alive = 3f;
        damage = 10;
    }

    public Stone(float x, float y, float speed, float direction) {
        super(null, x, y, speed, direction);
    }

    public Stone(Units owner, float x, float y, float speed, float direction) {
        super(owner, x, y, speed, direction);
    }

    @Override
    public void onCollided(CollideAble collideable) throws Exception {
        super.onCollided(collideable);
        if (collideable instanceof BulletBase) {
            alive = -1;
            Game.player.addScore(10);
        } else if (collideable instanceof WallBase wall) {
            alive = -1;
            return;
        }
        Game.addEffect(new BoomEffect(x, y, 1f, 3));
    }

    @Override
    public void render(GraphicsContext g) {
        super.render(g);
        addParticle(direction + 180, 60, 2);
        for (Particle particle : particles) {
            particle.move();
            particle.render(g);
        }
        particles.removeIf(particle -> particle.getAlive() < 0);
    }

    private void addParticle(float dir, float shakeDir, int num) {
        final float minSpeed = speed;
        final float maxSpeed = 1.5f * minSpeed;
        for (int i = 0; i < num; i++) {
            final double radians = Math.toRadians(
                    Interp.linear.apply(dir - shakeDir, dir + shakeDir, (float) Math.random()));
            final float dx = (float) Math.sin(radians);
            final float dy = (float) Math.cos(radians);
            particles.add(new Particle(
                                  x + radius * dx,
                                  y + radius * dy,
                                  Interp.linear.apply(minSpeed, maxSpeed, (float) Math.random()),
                                  dir) {
                              {
                                  alive = 0.75f;
                              }
                          }
            );
        }
    }
}

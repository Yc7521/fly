package com.fly.entities.effect;

import com.fly.entities.Particle;
import com.fly.math.Interp;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

public class BoomEffect extends EffectBase {
    private final float x, y; // boom src
    private final float time;
    public LinkedList<Particle> particles = new LinkedList<>();

    public BoomEffect(float x, float y, float time, float speed) {
        this.x = x;
        this.y = y;
        this.time = time;
        for (int i = 0; i < 200; i++) {
            particles.add(new Particle(
                    x,
                    y,
                    Interp.linear.apply(speed, 1.5f * speed, (float) Math.random()),
                    Interp.linear.apply(0, 359, (float) Math.random())
            ));
        }
    }

    @Override
    public void render(GraphicsContext g) {
        final float time = getTime();
        if (time >= this.time) {
            setEnd();
        }
        super.render(g);
        for (Particle particle : particles) {
            particle.move();
            particle.render(g);
        }
    }
}
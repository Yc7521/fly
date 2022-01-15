package com.fly.game.UI;

import com.fly.entities.RenderAble;
import com.fly.entities.util.Label;
import com.fly.game.Game;
import javafx.scene.canvas.GraphicsContext;

public class Frame implements RenderAble {
    private final Label hp;
    private final Label score;

    public Frame() {
        hp = new Label("hp: " + Game.player.getHp(), Game.maxX - 100, 30);
        score = new Label("score: " + Game.player.getScore(), Game.maxX - 100, 50);
    }

    @Override
    public void render(GraphicsContext g) {
        hp.setText("hp: " + Game.player.getHp());
        score.setText("score: " + Game.player.getScore());
        hp.render(g);
        score.render(g);
    }
}

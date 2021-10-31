package com.fly.game.Key;

import javafx.scene.input.KeyCode;

public class KeyMapping {
    public static KeyCode[] up = {KeyCode.UP, KeyCode.W};
    public static KeyCode[] down = {KeyCode.DOWN, KeyCode.S};
    public static KeyCode[] left = {KeyCode.LEFT, KeyCode.A};
    public static KeyCode[] right = {KeyCode.RIGHT, KeyCode.D};

    public static boolean in(KeyCode k, KeyCode[] keyCodes) {
        for (KeyCode keyCode : keyCodes) {
            if (k.equals(keyCode)) return true;
        }
        return false;
    }
}

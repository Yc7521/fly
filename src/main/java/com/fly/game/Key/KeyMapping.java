package com.fly.game.Key;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class KeyMapping {
    public static KeyCode[] up = {KeyCode.UP, KeyCode.W};
    public static KeyCode[] down = {KeyCode.DOWN, KeyCode.S};
    public static KeyCode[] left = {KeyCode.LEFT, KeyCode.A};
    public static KeyCode[] right = {KeyCode.RIGHT, KeyCode.D};
    public static KeyCode[] fight = {KeyCode.SPACE, KeyCode.F};
    public static MouseButton fight_b = MouseButton.PRIMARY;

    public static boolean in(KeyCode k, KeyCode[] keyCodes) {
        for (KeyCode keyCode : keyCodes) {
            if (k.equals(keyCode)) return true;
        }
        return false;
    }
}

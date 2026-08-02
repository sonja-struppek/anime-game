package de.sonja.game.jfx.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import de.sonja.game.jfx.model.Player;

public class InputController {

    private final Player player;

    public InputController(Scene scene, Player player) {
        this.player = player;

        scene.setOnKeyPressed(this::handleKeyPressed);
        scene.setOnKeyReleased(this::handleKeyReleased);
    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case A, LEFT -> player.moveLeft();
            case D, RIGHT -> player.moveRight();
            case SPACE, W, UP -> player.jump();
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case A, LEFT, D, RIGHT -> player.stopMoving();
        }
    }
}

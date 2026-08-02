package de.sonja.game.jfx.controller;

import javafx.scene.layout.Pane;
import de.sonja.game.jfx.model.Player;

public class CameraController {

    private double cameraX = 0;

    public void resetCamera() {
        cameraX = 0;
    }

    public void update(Player player, Pane root, double levelWidth) {

        double playerCenter = player.getTranslateX() + player.getWidth() / 2;
        double sceneCenter = 400; // Hälfte deiner Scene-Breite (800)

        // Kamera folgt dem Spieler
        cameraX = playerCenter - sceneCenter;

        // Begrenzung links
        if (cameraX < 0) {
            cameraX = 0;
        }

        // Begrenzung rechts
        double maxCameraX = levelWidth - 800; // Levelbreite - SceneBreite
        if (cameraX > maxCameraX) {
            cameraX = maxCameraX;
        }

        root.setTranslateX(-cameraX);
    }
}

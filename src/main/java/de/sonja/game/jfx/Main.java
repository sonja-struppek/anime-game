package de.sonja.game.jfx;

import de.sonja.game.jfx.model.Platform;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.sonja.game.jfx.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Player player;
    List<Platform> platforms = new ArrayList<>();
    private double cameraX = 0;


    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                case LEFT:
                    player.moveLeft();
                    break;

                case D:
                case RIGHT:
                    player.moveRight();
                    break;

                case SPACE:
                case W:
                case UP:
                    player.jump();
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A:
                case LEFT:
                case D:
                case RIGHT:
                    player.stopMoving();
                    break;
            }
        });

        // Player erstellen
        player = new Player(10, 100);
        root.getChildren().add(player);

        Platform ground = new Platform(0, 550, 5000, 50);
        Platform p1 = new Platform(100, 450, 120, 20);
        Platform p2 = new Platform(280, 350, 300, 200);

        platforms.add(ground);
        platforms.add(p1);
        platforms.add(p2);

        root.getChildren().addAll(ground, p1, p2);

        // Game Loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double levelWidth = 5000;
                double centerX = scene.getWidth() / 2;


                player.update();
                checkCollisions();

                // --- Spieler begrenzen ---
                if (player.getTranslateX() < 0) {
                    player.setTranslateX(0);
                }

                if (player.getTranslateX() + player.getWidth() > levelWidth) {
                    player.setTranslateX(levelWidth - player.getWidth());
                }

                // --- Kamera ---
                double offsetX = 0;

                if (player.getTranslateX() > centerX) {
                    offsetX = player.getTranslateX() - centerX;
                }

                // Kamera darf nicht über Level-Ende hinaus
                double maxCameraOffset = levelWidth - scene.getWidth();
                if (offsetX > maxCameraOffset) {
                    offsetX = maxCameraOffset;
                }

                // Kamera anwenden// Kamera anwenden
                cameraX += (offsetX - cameraX) * 0.1;
                root.setTranslateX(-cameraX);


            }
        };
        gameLoop.start();

        stage.setTitle("Anime Jump and Run");
        stage.setScene(scene);
        stage.show();
    }

    private void checkCollisions() {
        for (Platform platform : platforms) {

            var playerBounds = player.getBoundsInParent();
            var platformBounds = platform.getBoundsInParent();

            if (playerBounds.intersects(platformBounds)) {

                // Von oben landen
                if (player.getVelocityY() > 0 &&
                        playerBounds.getMaxY() <= platformBounds.getMinY() + 10) {

                    player.setTranslateY(platform.getTranslateY() - player.getHeight());
                    player.setVelocityY(0);
                    player.setOnGround(true);
                }

                // Von unten stoßen
                else if (player.getVelocityY() < 0 &&
                        playerBounds.getMinY() >= platformBounds.getMaxY() - 10) {

                    player.setTranslateY(platformBounds.getMaxY());
                    player.setVelocityY(0);
                }

                // Seitliche Kollision
                else {
                    // Player kommt von links
                    if (playerBounds.getMaxX() > platformBounds.getMinX() &&
                            playerBounds.getMinX() < platformBounds.getMinX()) {

                        player.setTranslateX(platformBounds.getMinX() - player.getWidth());
                    }

                    // Player kommt von rechts
                    else if (playerBounds.getMinX() < platformBounds.getMaxX() &&
                            playerBounds.getMaxX() > platformBounds.getMaxX()) {

                        player.setTranslateX(platformBounds.getMaxX());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
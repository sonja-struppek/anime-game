package de.sonja.game.jfx;

import de.sonja.game.jfx.controller.CameraController;
import de.sonja.game.jfx.controller.InputController;
import de.sonja.game.jfx.level.LevelManager;
import de.sonja.game.jfx.model.Platform;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.sonja.game.jfx.model.Player;

import java.util.ArrayList;
import java.util.List;



public class Main extends Application {

    private Player player;
    private List<Platform> platforms = new ArrayList<>();
    public LevelManager levelManager;
    private double levelWidth;
    private AnimationTimer gameLoop;
    private int currentLevelNumber = 1;
    private CameraController cameraController;



    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(root, 800, 600);

//        // --- Player erstellen ---
        player = new Player(10, 100);
        root.getChildren().add(player);

        new InputController(scene, player);
        cameraController = new CameraController();

        stage.setScene(scene);
        stage.setTitle("Anime Jump and Run");
        stage.show();

        // --- LevelManager erstellen und Level 1 laden ---
        levelManager = new LevelManager(this);
        levelManager.loadLevel(root, player, 1);

        platforms = levelManager.getPlatforms();
        levelWidth = levelManager.getLevelWidth();


        // --- Game Loop ---
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {


                if (levelManager.isGameWon(currentLevelNumber)) {
                    gameLoop.stop();
                    return;
                }

                player.update(scene.getHeight());
                checkCollisions();

                // --- Spieler begrenzen ---
                if (player.getTranslateX() < 0) {
                    player.setTranslateX(0);
                }

                if (player.getTranslateX() + player.getWidth() > levelWidth) {
                    player.setTranslateX(levelWidth - player.getWidth());
                }

                cameraController.update(player, root, levelWidth);

                if (player.getTranslateX() + player.getWidth() >= levelWidth) {

                    currentLevelNumber++;
                    if (levelManager.isGameWon(currentLevelNumber)) {
                        gameLoop.stop();
                    }
                    levelManager.loadLevel(root, player, currentLevelNumber);

                    platforms = levelManager.getPlatforms();
                    levelWidth = levelManager.getLevelWidth();
                }
            }
        };
        gameLoop.start();
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
                    if (playerBounds.getMaxX() > platformBounds.getMinX() &&
                            playerBounds.getMinX() < platformBounds.getMinX()) {

                        player.setTranslateX(platformBounds.getMinX() - player.getWidth());
                    }

                    else if (playerBounds.getMinX() < platformBounds.getMaxX() &&
                            playerBounds.getMaxX() > platformBounds.getMaxX()) {

                        player.setTranslateX(platformBounds.getMaxX());
                    }
                }
            }
        }
    }


    public AnimationTimer getGameLoop() {
        return gameLoop;
    }

    public static void main(String[] args) {
        launch();
    }

    private void pauseGame() {
        gameLoop.stop();
    }

    private void resumeGame() {
        gameLoop.start();
    }

    // In Main.java
    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public void setLevelWidth(double levelWidth) {
        this.levelWidth = levelWidth;
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public void setCurrentLevelNumber(int currentLevelNumber) {
        this.currentLevelNumber = currentLevelNumber;
    }

    public CameraController getCameraController() {
        return cameraController;
    }
}

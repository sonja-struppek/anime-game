package de.sonja.game.jfx.level;

import de.sonja.game.jfx.Main;
import de.sonja.game.jfx.model.Platform;
import de.sonja.game.jfx.model.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.List;


public class LevelManager {

    private GameLevel currentLevel;
    private Player playerRef;
    private Main mainRef;

    public LevelManager(Main main) {
        this.mainRef = main;
    }

    public void loadLevel(Pane root, Player player, int levelNumber) {
        this.playerRef = player;

        root.getChildren().clear();
        root.getChildren().add(player);

        switch (levelNumber) {
            case 1 -> currentLevel = new Level1();
            case 2 -> currentLevel = new Level2();
            default -> {
                showGameWonScreen(root);
                return;
            }
        }

        currentLevel.build(root, player);
    }

    public List<Platform> getPlatforms() {
        return currentLevel.getPlatforms();
    }

    public double getLevelWidth() {
        return currentLevel.getLevelWidth();
    }

    // ------------------------------------------------------------
    // GAME WON SCREEN
    // ------------------------------------------------------------
    public void showGameWonScreen(Pane root) {
        root.getChildren().clear();
        root.setTranslateX(0);
        mainRef.getCameraController().resetCamera();

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(root.getScene().getWidth(), root.getScene().getHeight());
        box.setStyle("-fx-background-color: black;");

        Label label = new Label("Du hast das Spiel gewonnen!");
        label.setStyle("-fx-font-size: 36px; -fx-text-fill: white;");

        Button restartButton = new Button("Nochmal spielen");
        restartButton.setStyle("-fx-font-size: 24px;");

        restartButton.setOnAction(e -> {
            mainRef.setCurrentLevelNumber(1);

            root.getChildren().clear();
            root.setTranslateX(0);
            mainRef.getCameraController().resetCamera();
            root.setStyle("-fx-background-color: white;");

            mainRef.levelManager.loadLevel(root, playerRef, 1);

            mainRef.setPlatforms(mainRef.levelManager.getPlatforms());
            mainRef.setLevelWidth(mainRef.levelManager.getLevelWidth());

            mainRef.getGameLoop().start();
        });

        box.getChildren().addAll(label, restartButton);
        root.getChildren().add(box);
    }

    public boolean isGameWon(int levelNumber) {
        return levelNumber > 2;
    }
}
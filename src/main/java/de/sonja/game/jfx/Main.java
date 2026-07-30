package de.sonja.game.jfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.sonja.game.jfx.model.Player;

public class Main extends Application {

    private Player player;

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
        player = new Player(100, 100);
        root.getChildren().add(player);

        // Game Loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.update();
            }
        };
        gameLoop.start();

        stage.setTitle("Anime Jump and Run");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
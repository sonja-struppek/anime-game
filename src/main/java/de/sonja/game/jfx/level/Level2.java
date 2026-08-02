package de.sonja.game.jfx.level;

import de.sonja.game.jfx.model.Player;
import de.sonja.game.jfx.model.Platform;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Level2 implements GameLevel{
    private final double levelWidth = 6500;
    private final List<Platform> platforms = new ArrayList<>();

    @Override
    public void build(Pane root, Player player) {

        double groundY = 600 - 50;

        // --- Boden ---
        Platform ground = new Platform(0, groundY, levelWidth, 50);

        // --- Plattformen ---
        Platform p1 = new Platform(300, groundY - 150, 150, 20);
        Platform p2 = new Platform(650, groundY - 250, 200, 20);
        Platform p3 = new Platform(1000, groundY - 120, 120, 20);

        // kleine Sprung-Inseln
        Platform p4 = new Platform(1400, groundY - 180, 100, 20);
        Platform p5 = new Platform(1600, groundY - 240, 100, 20);
        Platform p6 = new Platform(1800, groundY - 300, 100, 20);

        // große hohe Plattform
        Platform p7 = new Platform(2300, groundY - 350, 250, 20);

        // lange Laufstrecke
        Platform p8 = new Platform(3000, groundY - 100, 400, 20);

        platforms.add(ground);
        platforms.add(p1);
        platforms.add(p2);
        platforms.add(p3);
        platforms.add(p4);
        platforms.add(p5);
        platforms.add(p6);
        platforms.add(p7);
        platforms.add(p8);

        root.getChildren().addAll(platforms);

        // --- Player Startposition ---
        player.setTranslateX(100);
        player.setTranslateY(groundY - player.getHeight());
        player.setVelocityY(0);
        player.setOnGround(true);
    }

    @Override
    public List<Platform> getPlatforms() {
        return platforms;
    }

    @Override
    public double getLevelWidth() {
        return levelWidth;
    }


}

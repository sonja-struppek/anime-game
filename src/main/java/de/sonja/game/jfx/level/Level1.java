package de.sonja.game.jfx.level;

import de.sonja.game.jfx.model.Platform;
import de.sonja.game.jfx.model.Player;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Level1 implements GameLevel{

    private final double levelWidth = 5000;
    private final List<Platform> platforms = new ArrayList<>();

    @Override
    public void build(Pane root, Player player) {

        double groundHeight = 600 - 50;

        Platform ground = new Platform(0, groundHeight, levelWidth, 50);
        Platform p1 = new Platform(200, groundHeight - 120, 120, 20);
        Platform p2 = new Platform(450, groundHeight - 220, 200, 20);

        platforms.add(ground);
        platforms.add(p1);
        platforms.add(p2);

        root.getChildren().addAll(platforms);

        player.setTranslateX(100);
        player.setTranslateY(groundHeight - player.getHeight());
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

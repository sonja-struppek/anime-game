package de.sonja.game.jfx.level;

import de.sonja.game.jfx.model.Platform;
import de.sonja.game.jfx.model.Player;
import javafx.scene.layout.Pane;
import java.util.List;

public interface GameLevel {

    void build(Pane root, Player player);

    List<Platform> getPlatforms();

    double getLevelWidth();
}

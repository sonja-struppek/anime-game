module de.sonja.game.jfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.sonja.game.jfx to javafx.fxml;
    exports de.sonja.game.jfx;
    exports de.sonja.game.jfx.controller;
    opens de.sonja.game.jfx.controller to javafx.fxml;
}
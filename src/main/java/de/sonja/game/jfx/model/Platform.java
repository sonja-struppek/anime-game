package de.sonja.game.jfx.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Platform extends Rectangle {

    private double originalY;

    public Platform(double x, double y, double width, double height) {
        super(width, height, Color.GRAY);
        setTranslateX(x);
        setTranslateY(y);

        this.originalY = y;
    }
}


package de.sonja.game.jfx.model;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Player extends Rectangle {

    // Position wird von Rectangle geerbt
    private double velocityX = 0;
    private double velocityY = 0;

    private final double moveSpeed = 4;
    private final double gravity = 0.4;
    private final double jumpStrength = -10;

    private boolean onGround = false;

    public Player(double x, double y) {
        super(40, 60, Color.BLUE); // Breite, Höhe, Farbe
        setTranslateX(x);
        setTranslateY(y);
    }

    public void update() {
        // Schwerkraft anwenden
        velocityY += gravity;

        // Bewegung anwenden
        setTranslateX(getTranslateX() + velocityX);
        setTranslateY(getTranslateY() + velocityY);

        // Boden-Kollision (temporär)
        if (getTranslateY() >= 500) { // Boden bei y=500
            setTranslateY(500);
            velocityY = 0;
            onGround = true;
        }
    }

    public void moveLeft() {
        velocityX = -moveSpeed;
    }

    public void moveRight() {
        velocityX = moveSpeed;
    }

    public void stopMoving() {
        velocityX = 0;
    }

    public void jump() {
        if (onGround) {
            velocityY = jumpStrength;
            onGround = false;
        }
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double v) {
        this.velocityY = v;
    }

    public void setOnGround(boolean value) {
        this.onGround = value;
    }

}

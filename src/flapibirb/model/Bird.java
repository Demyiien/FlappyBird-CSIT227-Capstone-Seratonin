package flapibirb.model;

import flapibirb.controller.GObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Bird implements GObject {

    private Point pos;
    private String name;
    private int score;
    private boolean isAlive;
    private double velocity;
    private double gravity;

    private final int flightStepMs = 20; // matches GameTime's 4-tick (20ms) upward cadence
    private final int extraHangMs = 60;  // extra ms to negate gravity at peak after flight

    private boolean flying = false;
    private boolean hanging = false;
    private boolean canJump = true; // prevents holding the jump key to spam jump
    private Timer flightTimer;
    private Timer hangTimer;

    private final ImageIcon upperLeftIcon;
    private final ImageIcon upperRightIcon;
    private final ImageIcon lowerLeftIcon;
    private final ImageIcon lowerRightIcon;

    public Bird(String name) {
        this(name, new Point(8, 14), 24);
    }

    public Bird(String name, Point startPos, int cellSize) {
        this.name = name;
        this.pos = new Point(startPos.x, startPos.y);
        this.score = 0;
        this.isAlive = true;
        this.velocity = 7.5; // cells moved up per jump
        this.gravity = 0.5;  // cells moved down per gravity tick

        // Scale the bird to fit a 2x2 cell area (48x48 px)
        int birdWidth = 2 * cellSize;
        int birdHeight = 2 * cellSize;
        Image originalBird = new ImageIcon("assets/images/flappy-icon.png").getImage();

        BufferedImage bufferedBird = new BufferedImage(birdWidth, birdHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedBird.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalBird, 0, 0, birdWidth, birdHeight, null);
        g2d.dispose();

        // Split the bird image into 4 cells (upper-left, upper-right, lower-left, lower-right)
        upperLeftIcon = new ImageIcon(bufferedBird.getSubimage(0, 0, cellSize, cellSize));
        upperRightIcon = new ImageIcon(bufferedBird.getSubimage(cellSize, 0, cellSize, cellSize));
        lowerLeftIcon = new ImageIcon(bufferedBird.getSubimage(0, cellSize, cellSize, cellSize));
        lowerRightIcon = new ImageIcon(bufferedBird.getSubimage(cellSize, cellSize, cellSize, cellSize));
    }

    // Trigger upward flight window and peak hang time synced with GameTime's cadence
    public void jump() {
        if (!isAlive || !canJump) {
            return;
        }
        if (flightTimer != null && flightTimer.isRunning()) {
            flightTimer.stop();
        }
        if (hangTimer != null && hangTimer.isRunning()) {
            hangTimer.stop();
        }

        canJump = false;
        flying = true;
        hanging = false;

        int flightDuration = Math.max(1, (int) Math.round(velocity)) * flightStepMs;
        flightTimer = new Timer(flightDuration, e -> {
            flying = false;
            hanging = true;
            hangTimer = new Timer(extraHangMs, evt -> hanging = false);
            hangTimer.setRepeats(false);
            hangTimer.start();
        });
        flightTimer.setRepeats(false);
        flightTimer.start();
    }

    public void jump(JTable table) {
        jump();
    }

    @Override
    public void die() {
        isAlive = false;
        flying = false;
        hanging = false;
        if (flightTimer != null && flightTimer.isRunning()) {
            flightTimer.stop();
        }
        if (hangTimer != null && hangTimer.isRunning()) {
            hangTimer.stop();
        }
    }

    public boolean isFlying() {
        return flying;
    }

    public boolean isHanging() {
        return hanging;
    }

    // Re-enable jumping only after the player releases the jump key
    public void releaseJump() {
        canJump = true;
    }

    // Returns the matching quadrant icon if the given cell Point is part of the 2x2 bird
    public ImageIcon getIconAt(Point cellPos) {
        return getIconAt((int) cellPos.y, (int) cellPos.x);
    }

    public ImageIcon getIconAt(int r, int c) {
        int birdRow = (int) pos.y;
        int birdCol = (int) pos.x;

        if (r == birdRow && c == birdCol) {
            return upperLeftIcon;
        } else if (r == birdRow && c == birdCol + 1) {
            return upperRightIcon;
        } else if (r == birdRow + 1 && c == birdCol) {
            return lowerLeftIcon;
        } else if (r == birdRow + 1 && c == birdCol + 1) {
            return lowerRightIcon;
        }
        return null;
    }

    public int getRow() {
        return (int) pos.y;
    }

    public void setRow(int row) {
        pos.y = Math.max(0, row);
    }

    public int getCol() {
        return (int) pos.x;
    }

    public void setCol(int col) {
        pos.x = col;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
}

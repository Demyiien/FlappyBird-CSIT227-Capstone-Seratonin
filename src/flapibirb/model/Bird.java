package flapibirb.model;

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

    private final int flightStepMs = 50; // ms per upward cell movement
    private final int extraHangMs = 60;  // extra ms to negate gravity after flying up

    private boolean flying = false;
    private boolean canJump = true; // prevents holding the jump key to spam jump
    private Timer flightTimer;
    private Timer hangTimer;
    private JTable table;

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
        this.velocity = 3.0; // cells moved up per jump
        this.gravity = 1.0;  // cells moved down per gravity tick

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

    // Fly upward cell-by-cell using Point coordinates and negate gravity during flight
    public void jump() {
        if (!isAlive || !canJump) {
            return;
        }
        canJump = false;

        if (flightTimer != null && flightTimer.isRunning()) {
            flightTimer.stop();
        }
        if (hangTimer != null && hangTimer.isRunning()) {
            hangTimer.stop();
        }

        flying = true;
        int jumpCells = (int) Math.round(velocity);

        // Move up 1 cell right away on key press
        if (pos.y > 0) {
            setPos(new Point(pos.x, pos.y - 1));
            repaintTable();
        }

        final int[] remainingSteps = {Math.max(0, jumpCells - 1)};

        flightTimer = new Timer(flightStepMs, e -> {
            if (isAlive && remainingSteps[0] > 0 && pos.y > 0) {
                setPos(new Point(pos.x, pos.y - 1));
                remainingSteps[0]--;
                repaintTable();
            } else {
                ((Timer) e.getSource()).stop();

                // Keep gravity negated in proportion to flight time + extra ms
                int negateDuration = (jumpCells * flightStepMs) + extraHangMs;
                hangTimer = new Timer(negateDuration, evt -> {
                    flying = false;
                });
                hangTimer.setRepeats(false);
                hangTimer.start();
            }
        });
        flightTimer.start();
    }

    public void jump(JTable table) {
        this.table = table;
        jump();
    }

    @Override
    public void die() {
        isAlive = false;
        flying = false;
        if (flightTimer != null && flightTimer.isRunning()) {
            flightTimer.stop();
        }
        if (hangTimer != null && hangTimer.isRunning()) {
            hangTimer.stop();
        }
    }

    private void repaintTable() {
        if (table != null) {
            table.repaint();
        }
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public boolean isFlying() {
        return flying;
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

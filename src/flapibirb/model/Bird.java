package flapibirb.model;

import flapibirb.controller.GObject;
import flapibirb.controller.SoundManager;

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

    private static final double JUMP_VELOCITY = -1.03;
    private static final double DEFAULT_GRAVITY = 0.11;
    private static final double MAX_FALL_VELOCITY = 1.30;

    private static final double JUMP_UP_ANGLE = -25.0;
    private static final double MAX_FALL_ANGLE = 90.0;
    private static final double FALL_ROTATE_THRESHOLD = 0.20;
    private static final double FALL_ROTATE_BASE_SPEED = 7.5;
    private static final double FALL_ROTATE_VEL_SCALE = 7.5;

    private final int cellSize;
    private boolean canJump = true; // prevents holding the jump key to spam jump

    private final BufferedImage baseBirdImage;
    private double rotationAngle = 0.0;
    private int lastRenderedAngle = Integer.MIN_VALUE;

    private ImageIcon upperLeftIcon;
    private ImageIcon upperRightIcon;
    private ImageIcon lowerLeftIcon;
    private ImageIcon lowerRightIcon;

    public Bird(String name) {
        this(name, new Point(8, 14), 24);
    }

    public Bird(String name, Point startPos, int cellSize) {
        this.name = name;
        this.pos = new Point(startPos.x, startPos.y);
        this.score = 0;
        this.isAlive = true;
        this.velocity = 0.0;
        this.gravity = DEFAULT_GRAVITY;
        this.cellSize = cellSize;

        Image originalBird = new ImageIcon("assets/images/flappy-icon.png").getImage();
        int rawW = originalBird.getWidth(null);
        int rawH = originalBird.getHeight(null);

        if (rawW > 0 && rawH > 0) {
            BufferedImage fullBird = new BufferedImage(rawW, rawH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gRaw = fullBird.createGraphics();
            gRaw.drawImage(originalBird, 0, 0, null);
            gRaw.dispose();

            // Trim top-left mark and top padding on the 377x376 sprite so rotation pivots around the bird center
            if (rawW == 377 && rawH == 376) {
                baseBirdImage = fullBird.getSubimage(1, 71, 376, 282);
            } else {
                baseBirdImage = fullBird;
            }
        } else {
            baseBirdImage = new BufferedImage(2 * cellSize, 2 * cellSize, BufferedImage.TYPE_INT_ARGB);
        }

        updateRotatedIcons(0.0);
    }

    // Rotate the bird sprite within the 2x2 cell canvas and re-slice the 4 quadrant icons
    private void updateRotatedIcons(double angleDegrees) {
        int quantized = (int) Math.round(angleDegrees);
        if (quantized == lastRenderedAngle) {
            return;
        }
        lastRenderedAngle = quantized;

        int canvasSize = 2 * cellSize;
        double maxDim = canvasSize * 0.80;
        double srcW = baseBirdImage.getWidth();
        double srcH = baseBirdImage.getHeight();
        double scale = Math.min(maxDim / srcW, maxDim / srcH);
        int drawW = Math.max(1, (int) Math.round(srcW * scale));
        int drawH = Math.max(1, (int) Math.round(srcH * scale));

        BufferedImage rotated = new BufferedImage(canvasSize, canvasSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(quantized), canvasSize / 2.0, canvasSize / 2.0);
        g2d.drawImage(baseBirdImage, (canvasSize - drawW) / 2, (canvasSize - drawH) / 2, drawW, drawH, null);
        g2d.dispose();

        upperLeftIcon = new ImageIcon(rotated.getSubimage(0, 0, cellSize, cellSize));
        upperRightIcon = new ImageIcon(rotated.getSubimage(cellSize, 0, cellSize, cellSize));
        lowerLeftIcon = new ImageIcon(rotated.getSubimage(0, cellSize, cellSize, cellSize));
        lowerRightIcon = new ImageIcon(rotated.getSubimage(cellSize, cellSize, cellSize, cellSize));
    }

    // Apply instant upward flap impulse and tilt nose up
    public void jump() {
        if (!isAlive || !canJump) {
            return;
        }
        SoundManager.play("fly");
        canJump = false;
        velocity = JUMP_VELOCITY;
        setRotationAngle(JUMP_UP_ANGLE);
    }

    public void jump(JTable table) {
        jump();
    }

    // Advance parabolic vertical velocity/position and pitch nose down smoothly while falling
    public void updatePhysics(int totalRows) {
        if (!isAlive) {
            return;
        }

        velocity = Math.min(MAX_FALL_VELOCITY, velocity + gravity);
        pos.y += velocity;

        double maxRow = totalRows - 2;
        if (pos.y < 0.0) {
            pos.y = 0.0;
            if (velocity < 0.0) {
                velocity = 0.0;
            }
        } else if (pos.y >= maxRow) {
            pos.y = maxRow;
            velocity = 0.0;
        }

        if (velocity < FALL_ROTATE_THRESHOLD) {
            setRotationAngle(JUMP_UP_ANGLE);
        } else if (pos.y < maxRow) {
            double rotateStep = FALL_ROTATE_BASE_SPEED + (velocity * FALL_ROTATE_VEL_SCALE);
            setRotationAngle(rotationAngle + rotateStep);
        }
    }

    public void setRotationAngle(double angleDegrees) {
        rotationAngle = Math.max(JUMP_UP_ANGLE, Math.min(MAX_FALL_ANGLE, angleDegrees));
        updateRotatedIcons(rotationAngle);
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public void die() {
        SoundManager.play("hit");
        SoundManager.play("die");
        isAlive = false;
        velocity = 0.0;
    }

    public boolean isFlying() {
        return velocity < 0.0;
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
        int birdRow = Math.max(0, (int) Math.round(pos.y));
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
        return Math.max(0, (int) Math.round(pos.y));
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

package flapibirb.model;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Bird {

    private int row;
    private int col;

    private final int jumpCells = 3;      // cells moved up per jump
    private final int flightStepMs = 50;  // ms per upward cell movement
    private final int extraHangMs = 60;  // extra ms to negate gravity after flying up

    private boolean flying = false;
    private boolean canJump = true; // prevents holding the jump key to spam jump
    private Timer flightTimer;
    private Timer hangTimer;

    private final ImageIcon upperLeftIcon;
    private final ImageIcon upperRightIcon;
    private final ImageIcon lowerLeftIcon;
    private final ImageIcon lowerRightIcon;

    public Bird(int startRow, int startCol, int cellSize) {
        this.row = startRow;
        this.col = startCol;

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

    // Fly upward cell-by-cell and negate gravity during flight plus extra hang time
    public void jump(JTable table) {
        if (!canJump) {
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

        // Move up 1 cell right away on key press
        if (row > 0) {
            row--;
            table.repaint();
        }

        final int[] remainingSteps = {jumpCells - 1};

        flightTimer = new Timer(flightStepMs, e -> {
            if (remainingSteps[0] > 0 && row > 0) {
                row--;
                remainingSteps[0]--;
                table.repaint();
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

    public boolean isFlying() {
        return flying;
    }

    // Re-enable jumping only after the player releases the jump key
    public void releaseJump() {
        canJump = true;
    }

    // Returns the matching quadrant icon if the given cell is part of the 2x2 bird
    public ImageIcon getIconAt(int r, int c) {
        if (r == row && c == col) {
            return upperLeftIcon;
        } else if (r == row && c == col + 1) {
            return upperRightIcon;
        } else if (r == row + 1 && c == col) {
            return lowerLeftIcon;
        } else if (r == row + 1 && c == col + 1) {
            return lowerRightIcon;
        }
        return null;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Bird {

    private int row;
    private int col;

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

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Pipe {

    private int col;
    private final int pipeCols = 3; // pipe is 3 cells wide
    private final int minPipeHeight = 2; // minimum size is 3x2 for both pipes
    private final int gapRows = 8; // non-collision space between top and bottom pipes

    private int topPipeHeight;
    private int bottomPipeStartRow;

    private final ImageIcon[] pipeTopIcons;
    private final ImageIcon[] pipeBottomIcons;

    public Pipe(int startCol, int totalRows, int cellSize) {
        this.col = startCol;

        // Create pipe tip graphic
        Image RawPipeTip = new ImageIcon("assets/images/pipe-tip.png").getImage();
        BufferedImage pipeTipImage = new BufferedImage(
                RawPipeTip.getWidth(null),
                RawPipeTip.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D graphicsPipeTip = pipeTipImage.createGraphics();
        graphicsPipeTip.drawImage(RawPipeTip, 0, 0, null);
        graphicsPipeTip.dispose();

        // Create pipe hose graphic
        Image RawPipeHose = new ImageIcon("assets/images/pipe-hose.png").getImage();
        BufferedImage pipeHoseImage = new BufferedImage(
                RawPipeTip.getWidth(null),
                RawPipeTip.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D graphicsPipeHose = pipeHoseImage.createGraphics();
        graphicsPipeHose.drawImage(RawPipeHose, 0, 0, null);
        graphicsPipeHose.dispose();

        // Resize the pipe rim and body to match the width of 3 cells
        BufferedImage scaledPipeTop = new BufferedImage(pipeCols * cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gTop = scaledPipeTop.createGraphics();
        gTop.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gTop.drawImage(pipeTipImage, 0, 0, pipeCols * cellSize, cellSize, null);
        gTop.dispose();

        BufferedImage scaledPipeBottom = new BufferedImage(pipeCols * cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBot = scaledPipeBottom.createGraphics();
        gBot.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gBot.drawImage(pipeHoseImage, 0, 0, pipeCols * cellSize, cellSize, null);
        gBot.dispose();

        // Slice the pipe rim and body into 3 columns (left, middle, right)
        pipeTopIcons = new ImageIcon[pipeCols];
        pipeBottomIcons = new ImageIcon[pipeCols];
        for (int c = 0; c < pipeCols; c++) {
            pipeTopIcons[c] = new ImageIcon(scaledPipeTop.getSubimage(c * cellSize, 0, cellSize, cellSize));
            pipeBottomIcons[c] = new ImageIcon(scaledPipeBottom.getSubimage(c * cellSize, 0, cellSize, cellSize));
        }

        randomizeHeight(totalRows);
    }

    // Randomize top and bottom pipe heights while keeping at least 3x2 cells for each
    public void randomizeHeight(int totalRows) {
        Random random = new Random();
        int maxTopHeight = totalRows - gapRows - minPipeHeight;
        this.topPipeHeight = minPipeHeight + random.nextInt(maxTopHeight - minPipeHeight + 1);
        this.bottomPipeStartRow = topPipeHeight + gapRows;
    }

    // Returns the pipe icon for a given cell, or null if the cell is in the gap or outside the pipe
    public ImageIcon getIconAt(int r, int c) {
        if (c < col || c >= col + pipeCols) {
            return null;
        }

        int pipePartCol = c - col;

        // Top pipe: body on top, rim right above the gap
        if (r < topPipeHeight - 1) {
            return pipeBottomIcons[pipePartCol];
        } else if (r == topPipeHeight - 1) {
            return pipeTopIcons[pipePartCol];
        }
        // Bottom pipe: rim right below the gap, body going down to the bottom
        else if (r == bottomPipeStartRow) {
            return pipeTopIcons[pipePartCol];
        } else if (r > bottomPipeStartRow) {
            return pipeBottomIcons[pipePartCol];
        }

        return null;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

}

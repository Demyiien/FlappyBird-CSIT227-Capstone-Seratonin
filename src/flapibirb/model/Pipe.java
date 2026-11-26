package flapibirb.model;

import flapibirb.controller.GObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Pipe implements GObject {

    private final int pipeX;
    private final int pipeY;
    private final int width;
    private final int height;

    private final ImageIcon[] pipeTopIcons;
    private final ImageIcon[] pipeBottomIcons;

    public Pipe(int startCol, int totalRows, int cellSize) {
        Random random = new Random();
        width = random.nextInt(2, 5); // width
        height = random.nextInt(8, 10); // height

        pipeX = startCol;
        pipeY = random.nextInt(2, totalRows-2-height);

        // Load split pipe tip and hose images
        Image rawPipeTipLeft = new ImageIcon("assets/images/pipe-tip-left.png").getImage();
        BufferedImage pipeTipLeft = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphicsPipe = pipeTipLeft.createGraphics();
        graphicsPipe.drawImage(rawPipeTipLeft, 0, 0, null);

        Image rawPipeTipCenter = new ImageIcon("assets/images/pipe-tip-center.png").getImage();
        BufferedImage pipeTipCenter = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        graphicsPipe = pipeTipCenter.createGraphics();
        graphicsPipe.drawImage(rawPipeTipCenter, 0, 0, null);

        Image rawPipeTipRight = new ImageIcon("assets/images/pipe-tip-right.png").getImage();
        BufferedImage pipeTipRight = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        graphicsPipe = pipeTipRight.createGraphics();
        graphicsPipe.drawImage(rawPipeTipRight, 0, 0, null);

        Image rawPipeHoseLeft = new ImageIcon("assets/images/pipe-hose-left.png").getImage();
        BufferedImage pipeHoseLeft = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        graphicsPipe = pipeHoseLeft.createGraphics();
        graphicsPipe.drawImage(rawPipeHoseLeft, 0, 0, null);

        Image rawPipeHoseCenter = new ImageIcon("assets/images/pipe-hose-center.png").getImage();
        BufferedImage pipeHoseCenter = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        graphicsPipe = pipeHoseCenter.createGraphics();
        graphicsPipe.drawImage(rawPipeHoseCenter, 0, 0, null);

        Image rawPipeHoseRight = new ImageIcon("assets/images/pipe-hose-right.png").getImage();
        BufferedImage pipeHoseRight = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        graphicsPipe = pipeHoseRight.createGraphics();
        graphicsPipe.drawImage(rawPipeHoseRight, 0, 0, null);
        graphicsPipe.dispose();

        // Slice the pipe rim and body into 3 columns (left, middle, right)
        pipeTopIcons = new ImageIcon[width];
        pipeBottomIcons = new ImageIcon[width];
        for (int c = 0; c < width; c++) {
            pipeTopIcons[c] = new ImageIcon(c == 0 ? pipeTipLeft : c == width-1 ? pipeTipRight : pipeTipCenter);
            pipeBottomIcons[c] = new ImageIcon(c == 0 ? pipeHoseLeft : c == width-1 ? pipeHoseRight : pipeHoseCenter);
        }
    }

    // Returns the pipe icon for a given cell, or null if the cell is in the gap or outside the pipe
    public ImageIcon getIconAt(int r, int c) {
        if (c < pipeX || c >= pipeX + width) {
            return null;
        }

        int pipePartCol = c - pipeX;

        // Top pipe: body on top, rim right above the gap
        if (r < pipeY - 1) {
            return pipeBottomIcons[pipePartCol];
        } else if (r == pipeY - 1) {
            return pipeTopIcons[pipePartCol];
        }
        // Bottom pipe: rim right below the gap, body going down to the bottom
        else if (r == pipeY + height) {
            return pipeTopIcons[pipePartCol];
        } else if (r > pipeY + height) {
            return pipeBottomIcons[pipePartCol];
        }

        return null;
    }

    public boolean inPipe(double x, double y, int w, int h) {
        if (pipeX <= x && x < pipeX + width - 1) {
            return !(pipeY <= y && y < pipeY + height - 1);
        }
        return false;
    }
    
    public int getCol() {
        return pipeX;
    }

    @Override
    public void die() {
    }
}

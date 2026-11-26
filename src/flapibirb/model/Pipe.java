package flapibirb.model;

import flapibirb.controller.GObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Pipe implements GObject {

    private static final Random RANDOM = new Random();

    // cached pipe sprites so we don't reload 6 pngs from disk on every pipe spawn
    private static int cachedCellSize = -1;
    private static ImageIcon tipLeftIcon;
    private static ImageIcon tipCenterIcon;
    private static ImageIcon tipRightIcon;
    private static ImageIcon hoseLeftIcon;
    private static ImageIcon hoseCenterIcon;
    private static ImageIcon hoseRightIcon;

    private final int pipeX;
    private final int pipeY;
    private final int width;
    private final int height;

    private final ImageIcon[] pipeTopIcons;
    private final ImageIcon[] pipeBottomIcons;

    public Pipe(int startCol, int totalRows, int cellSize) {
        width = RANDOM.nextInt(2, 5);
        height = RANDOM.nextInt(8, 10);

        pipeX = startCol;
        pipeY = RANDOM.nextInt(2, totalRows - 2 - height);

        ensureSpritesLoaded(cellSize);

        // build left, middle, and right columns for the pipe rim and body
        pipeTopIcons = new ImageIcon[width];
        pipeBottomIcons = new ImageIcon[width];
        for (int c = 0; c < width; c++) {
            pipeTopIcons[c] = (c == 0) ? tipLeftIcon : (c == width - 1) ? tipRightIcon : tipCenterIcon;
            pipeBottomIcons[c] = (c == 0) ? hoseLeftIcon : (c == width - 1) ? hoseRightIcon : hoseCenterIcon;
        }
    }

    // load and scale all 6 pipe slice sprites once per cell size
    private static void ensureSpritesLoaded(int cellSize) {
        if (cachedCellSize == cellSize) {
            return;
        }
        tipLeftIcon = loadScaledIcon("assets/images/pipe-tip-left.png", cellSize);
        tipCenterIcon = loadScaledIcon("assets/images/pipe-tip-center.png", cellSize);
        tipRightIcon = loadScaledIcon("assets/images/pipe-tip-right.png", cellSize);
        hoseLeftIcon = loadScaledIcon("assets/images/pipe-hose-left.png", cellSize);
        hoseCenterIcon = loadScaledIcon("assets/images/pipe-hose-center.png", cellSize);
        hoseRightIcon = loadScaledIcon("assets/images/pipe-hose-right.png", cellSize);
        cachedCellSize = cellSize;
    }

    // draw raw png into an argb buffer sized to cellSize and dispose graphics
    private static ImageIcon loadScaledIcon(String path, int cellSize) {
        Image raw = new ImageIcon(path).getImage();
        BufferedImage buf = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buf.createGraphics();
        g2.drawImage(raw, 0, 0, cellSize, cellSize, null);
        g2.dispose();
        return new ImageIcon(buf);
    }

    // returns the pipe icon for a given cell, or null if the cell is in the gap or outside the pipe
    public ImageIcon getIconAt(int r, int c) {
        if (c < pipeX || c >= pipeX + width) {
            return null;
        }

        int pipePartCol = c - pipeX;

        // top pipe: body on top, rim right above the gap
        if (r < pipeY - 1) {
            return pipeBottomIcons[pipePartCol];
        } else if (r == pipeY - 1) {
            return pipeTopIcons[pipePartCol];
        }
        // bottom pipe: rim right below the gap, body going down to the bottom
        else if (r == pipeY + height) {
            return pipeTopIcons[pipePartCol];
        } else if (r > pipeY + height) {
            return pipeBottomIcons[pipePartCol];
        }

        return null;
    }

    // check aabb overlap between the bird hitbox (x, y, w, h) and the top/bottom pipe segments
    public boolean inPipe(double x, double y, int w, int h) {
        boolean overlapsX = x < pipeX + width && x + w > pipeX;
        if (!overlapsX) {
            return false;
        }
        return y < pipeY || y + h > pipeY + height;
    }

    public int getCol() {
        return pipeX;
    }

    @Override
    public void die() {
    }
}

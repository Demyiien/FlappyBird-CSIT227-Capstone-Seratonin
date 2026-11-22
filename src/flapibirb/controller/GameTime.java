package flapibirb.controller;

import javax.swing.*;
import flapibirb.model.*;
import flapibirb.view.Game;

public class GameTime {

    private final Timer timer;
    private long ticks = 0;

    public GameTime(Game game) {
        JTable table = game.getGameTable();
        Bird bird = game.getBird();
        int rows = game.getRows();

        timer = new Timer(5, e -> {
            game.handleObjects();

            if (bird.isAlive()) {
                if (ticks % 4 == 0 && bird.isFlying()) {
                    bird.setRow(bird.getRow() - 1);
                }
                if (ticks % 6 == 0 && bird.isHanging()) {
                    bird.rotateTowardLevel();
                }
                if (ticks % 6 == 0 && !bird.isFlying() && !bird.isHanging()) {
                    if (bird.getRow() < rows - 2) {
                        bird.setRow(bird.getRow() + Math.max(1, (int) Math.round(bird.getGravity())));
                        bird.rotateDown();
                    }
                }
                if (ticks % 10 == 0) {
                    bird.setCol(bird.getCol() + 1);
                    game.setCameraX(game.getCameraX() + 1);
                }
                if (ticks % 250 == 0) {
                    game.addPipe();
                }
            }

            table.repaint();
            ticks++;
        });
    }

    public void start() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    public void stop() {
        timer.stop();
    }
}

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
                if (ticks % 2 == 0) {
                    bird.updatePhysics(rows);
                }
                if (ticks % 7 == 0) {
                    bird.setCol(bird.getCol() + 1);
                    game.setCameraX(game.getCameraX() + 1);
                }
                if (ticks % 125 == 0) {
                    game.addPipe();
                }

                // check floor and pipe collisions every tick so fast falls don't skip past
                if (bird.getPos().y >= rows - 2) {
                    bird.die();
                } else {
                    for (Pipe pipe : game.getPipes()) {
                        if (pipe.inPipe(bird.getPos().x, bird.getPos().y, 2, 2)) {
                            bird.die();
                            break;
                        }
                    }
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

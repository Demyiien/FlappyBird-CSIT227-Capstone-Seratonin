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

                    if (!game.isDead()) {
                        if ((int) bird.getPos().y == rows - 2) {
                            System.out.println("dead");
                            game.die();
                        } else if (!game.getPipes().isEmpty()) {
                            for (Pipe i : game.getPipes()) {
                                if (i.inPipe(bird.getPos().x, bird.getPos().y, 2, 2)) game.die();
                            }
                        }
                    }
                    else {
                        bird.jump();
                    }
                }
                if (ticks % 125 == 0) {
                    game.addPipe();
                }
            }

            // System.out.println("dead = " + game.isDead());
            /* for (Pipe i: game.getPipes()) {
                if (i.inPipe(bird.getPos().x, bird.getPos().y, 2, 2) && game.isDead() == 0) {
                    game.toggleDead();
                    break;
                }
            } */

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

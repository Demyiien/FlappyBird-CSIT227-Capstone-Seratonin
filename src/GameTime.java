import javax.swing.*;

public class GameTime {

    private final Timer timer;

    public GameTime(Game game) {
        JTable table = game.getGameTable();
        Bird bird = game.getBird();
        int rows = game.getRows();
        timer = new Timer(35, e -> {
            if (bird.isFlying()) {
                return;
            }

            // Bird is 2x2 cells, so the lowest top-row before hitting the floor is totalRows - 2
            if (bird.getRow() < rows - 2) {
                bird.setRow(bird.getRow() + 1);
                table.repaint();
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}

import javax.swing.*;

public class Gravity {

    private final Timer timer;

    public Gravity(Bird bird, JTable table, int totalRows) {
        timer = new Timer(25, e -> {
            if (bird.isFlying()) {
                return;
            }

            // Bird is 2x2 cells, so the lowest top-row before hitting the floor is totalRows - 2
            if (bird.getRow() < totalRows - 2) {
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

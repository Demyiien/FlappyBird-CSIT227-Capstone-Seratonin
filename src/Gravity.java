import javax.swing.*;

public class Gravity {

    private final Timer timer;

    public Gravity(Bird bird, JTable table, int totalRows) {
        timer = new Timer(35, e -> {
            if (!bird.isAlive() || bird.isFlying()) {
                return;
            }

            // Bird is 2x2 cells, so the lowest top-row before hitting the floor is totalRows - 2
            Point pos = bird.getPos();
            if (pos.y < totalRows - 2) {
                double nextY = Math.min(totalRows - 2, pos.y + bird.getGravity());
                bird.setPos(new Point(pos.x, nextY));
                table.repaint();
            }
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

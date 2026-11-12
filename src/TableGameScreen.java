import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.*;

public class TableGameScreen extends JFrame {

    private JTable table1;
    private JPanel panel1;
    private JScrollPane scrollPane;

    //TRIN test additions-----------
    private String scoreText = "0";
    private int counter = 0;
    //TRIN test additions-----------

    public TableGameScreen() {

        // Load and draw the background image on the main panel
        Image backgroundImage = new ImageIcon("assets/images/background.jpg").getImage();

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                scoreText = "" + counter;

                // THIS is what updates the value of the label
                repaint();
            }
        });
        timer.start();

        panel1 = new JPanel(new GridBagLayout()) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }

                // TEST-----------
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 72));
                g.drawString(scoreText, 1000, 100);

            }
        };

        // 32x64 grid with 24px cells (total size is 1536x768)
        int rows = 32;
        int cols = 64;
        int cellSize = 24;

        DefaultTableModel model = new DefaultTableModel(rows, cols);

        // Initialize the bird and pipe objects
        Bird bird = new Bird(14, 8, cellSize);
        Pipe pipe = new Pipe(36, rows, cellSize);

        table1 = new JTable(model);

        table1.setRowHeight(cellSize);

        for (int i = 0; i < cols; i++) {
            table1.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
            table1.getColumnModel().getColumn(i).setMinWidth(cellSize);
            table1.getColumnModel().getColumn(i).setMaxWidth(cellSize);
        }

        table1.setIntercellSpacing(new Dimension(0, 0));
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setShowGrid(true);
        table1.setGridColor(Color.GRAY);
        table1.setTableHeader(null);
        table1.setFocusable(false);

        // Make table and scrollPane transparent so the background shows through
        Color alphaZero = new Color(0, 0, 0, 0);
        table1.setOpaque(false);
        table1.setBackground(alphaZero);

        if (scrollPane != null) {
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.getViewport().setBackground(alphaZero);
        }

        table1.setPreferredScrollableViewportSize(new Dimension(cols * cellSize, rows * cellSize));

        panel1.setFocusable(true);
        panel1.add(table1, new GridBagConstraints());

        setTitle("Table Game Screen");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        table1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                // Clear cell defaults so previous icons don't repeat on empty cells
                cell.setOpaque(false);
                cell.setBackground(alphaZero);
                cell.setBorder(null);
                cell.setText("");
                cell.setIcon(null);

                // Check if the current cell belongs to the bird or the pipe
                ImageIcon birdIcon = bird.getIconAt(row, column);
                if (birdIcon != null) {
                    cell.setIcon(birdIcon);
                } else {
                    ImageIcon pipeIcon = pipe.getIconAt(row, column);
                    if (pipeIcon != null) {
                        cell.setIcon(pipeIcon);
                    }
                }

                return cell;
            }
        });

        // Initialize gravity, but wait for the first key press before starting it
        Gravity gravity = new Gravity(bird, table1, rows);

        // Listen globally for SPACE, UP, or W key presses and releases (prevents holding to spam jump)
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (!gravity.isRunning()) {
                        gravity.start();
                    }
                    bird.jump(table1);
                    return true;
                } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                    bird.releaseJump();
                    return true;
                }
            }
            return false;
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            TableGameScreen screen = new TableGameScreen();
            screen.setVisible(true);
        });

    }
}

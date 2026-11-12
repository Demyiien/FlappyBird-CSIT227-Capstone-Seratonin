import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends JFrame {

    private JTable gameTable;
    private JPanel mainPanel;
    private JScrollPane mainScrollPane;

    private final int rows = 32;
    private final int cols = 64;
    private final int cellSize = 24;
    private Bird bird;
    private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    private int cameraX;

    private GameTime gameTime;


    public Game() {

        // Load and draw the background image on the main panel
        Image backgroundImage = new ImageIcon("assets/images/background.jpg").getImage();

        mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // 32x64 grid with 24px cells (total size is 1536x768)


        DefaultTableModel model = new DefaultTableModel(rows, cols);

        // Initialize the bird and pipe objects
        Pipe pipe = new Pipe(36, rows, cellSize);

        bird = new Bird(14, 8, cellSize);
        pipes.add(new Pipe(36, rows, cellSize));
        gameTable = new JTable(model);
        gameTime = new GameTime(this);

        gameTable.setRowHeight(cellSize);

        for (int i = 0; i < cols; i++) {
            gameTable.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
            gameTable.getColumnModel().getColumn(i).setMinWidth(cellSize);
            gameTable.getColumnModel().getColumn(i).setMaxWidth(cellSize);
        }

        gameTable.setIntercellSpacing(new Dimension(0, 0));
        gameTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        gameTable.setShowGrid(true);
        gameTable.setGridColor(Color.GRAY);
        gameTable.setTableHeader(null);
        gameTable.setFocusable(false);

        // Make table and mainScrollPane transparent so the background shows through
        Color alphaZero = new Color(0, 0, 0, 0);
        gameTable.setOpaque(false);
        gameTable.setBackground(alphaZero);

        if (mainScrollPane != null) {
            mainScrollPane.setOpaque(false);
            mainScrollPane.getViewport().setOpaque(false);
            mainScrollPane.getViewport().setBackground(alphaZero);
        }

        gameTable.setPreferredScrollableViewportSize(new Dimension(cols * cellSize, rows * cellSize));

        mainPanel.setFocusable(true);
        mainPanel.add(gameTable, new GridBagConstraints());

        setTitle("Flappy Birb");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        gameTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {

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

        // Listen globally for SPACE, UP, or W key presses to make the bird fly
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    bird.jump(gameTable);
                    return true;
                }
            }
            return false;
        });

        // Start gravity so the bird falls 1 cell every 0.1 seconds
        gameTime.start();
    }

    public JTable getGameTable() {
        return gameTable;
    }

    public Bird getBird() {
        return bird;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Game screen = new Game();
            screen.setVisible(true);
        });
    }
}

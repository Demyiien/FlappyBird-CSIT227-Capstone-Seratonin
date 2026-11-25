package flapibirb.view;

import flapibirb.controller.GameTime;
import flapibirb.model.Bird;
import flapibirb.model.CoolBird;
import flapibirb.model.Pipe;
import flapibirb.model.Point;
import flapibirb.model.Scoreboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends JFrame {

    private JTable gameTable;
    private JPanel mainPanel;
    private JScrollPane mainScrollPane;

    private final int rows = 32;
    private final int cols = 64;
    private final int cellSize = 24;

    private Bird bird;
    private final ArrayList<Pipe> pipes = new ArrayList<>();
    private final Point cameraPos = new Point(0, 0);
    private final Scoreboard scoreboard = new Scoreboard();

    private GameTime gameTime;
    private Timer scoreTimer;

    public Game() {
        loadScoreFont();

        // Initialize the bird (using Point(x=col, y=row)) and game table
        bird = new CoolBird("Player", new Point(8, 14), cellSize);
        initializeTable();

        scoreTimer = new Timer(1000, e -> {
            if (bird.isAlive()) {
                scoreboard.increment();
                mainPanel.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });

        setTitle("Flappy Birb");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(1600, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        gameTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            private ImageIcon pipeLayerIcon;
            private ImageIcon birdLayerIcon;

            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                // Clear cell defaults so previous icons don't repeat on empty cells
                cell.setOpaque(false);
                cell.setBackground(new Color(0, 0, 0, 0));
                cell.setBorder(null);
                cell.setText("");
                cell.setIcon(null);

                int worldCol = column + getCameraX();

                // Layer order: BGPhoto (mainPanel) -> Pipe -> Bird
                birdLayerIcon = bird.getIconAt(new Point(worldCol, row));
                pipeLayerIcon = null;
                for (Pipe pipe : pipes) {
                    ImageIcon pipeIcon = pipe.getIconAt(row, worldCol);
                    if (pipeIcon != null) {
                        pipeLayerIcon = pipeIcon;
                        break;
                    }
                }

                return cell;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (pipeLayerIcon != null) {
                    pipeLayerIcon.paintIcon(this, g, 0, 0);
                }
                if (birdLayerIcon != null) {
                    birdLayerIcon.paintIcon(this, g, 0, 0);
                }
            }
        });

        // Wait for the first jump key press to start GameTime & scoreboard, and require key release between jumps
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (!gameTime.isRunning()) {
                        gameTime.start();
                    }
                    if (!scoreTimer.isRunning()) {
                        scoreTimer.start();
                    }
                    bird.jump();
                    return true;
                } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                    bird.releaseJump();
                    return true;
                }
            }
            return false;
        });
    }

    private void loadScoreFont() {
        try {
            File fontFile = new File("assets/images/NumberFont.ttf");
            if (fontFile.exists()) {
                Font numberFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(numberFont);
            }
        } catch (IOException | FontFormatException e) {
            System.err.println("Could not load score font: " + e.getMessage());
        }
    }

    public void initializeTable() {
        Image backgroundImage = new ImageIcon("assets/images/blueBG.jpg").getImage();
        mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }

                // Draw score overlay at top-center with shadow for readability
                g.setFont(new Font("Flappy Bird", Font.PLAIN, 82));
                String scoreText = scoreboard.getScoreText();
                FontMetrics metrics = g.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(scoreText)) / 2;
                int y = 100;

                g.setColor(Color.BLACK);
                g.drawString(scoreText, x + 3, y + 3);
                g.setColor(Color.WHITE);
                g.drawString(scoreText, x, y);
            }
        };

        // 32x64 grid with 24px cells (total size is 1536x768)
        DefaultTableModel model = new DefaultTableModel(rows, cols);

        gameTable = new JTable(model);
        gameTable.setRowHeight(cellSize);

        gameTime = new GameTime(this);
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
        gameTable.setOpaque(false);
        gameTable.setBackground(new Color(0, 0, 0, 0));

        if (mainScrollPane != null) {
            mainScrollPane.setOpaque(false);
            mainScrollPane.getViewport().setOpaque(false);
            mainScrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        }

        gameTable.setPreferredScrollableViewportSize(new Dimension(cols * cellSize, rows * cellSize));

        mainPanel.setFocusable(true);
        mainPanel.add(gameTable, new GridBagConstraints());
    }

    public JTable getGameTable() {
        return gameTable;
    }

    public Bird getBird() {
        return bird;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCameraX() {
        return (int) cameraPos.x;
    }

    public void setCameraX(int cameraX) {
        cameraPos.x = cameraX;
    }

    public Point getCameraPos() {
        return cameraPos;
    }

    public void handleObjects() {
        pipes.removeIf(pipe -> pipe.getCol() - getCameraX() <= -3);
    }

    public void addPipe() {
        pipes.add(new Pipe(cols + 1 + getCameraX(), rows, cellSize));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game screen = new Game();
            screen.setVisible(true);
        });
    }
}

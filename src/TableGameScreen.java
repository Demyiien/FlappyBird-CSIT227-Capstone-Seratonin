import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TableGameScreen extends JFrame {

    private JTable table1;
    private JPanel panel1;
    private JScrollPane scrollPane;

    public TableGameScreen() {

        // Load and draw the background image on the main panel
        Image backgroundImage = new ImageIcon("assets/images/background.jpg").getImage();

        panel1 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // 32x64 grid with 24px cells (total size is 1536x768)
        int rows = 32;
        int cols = 64;
        int cellSize = 24;

        DefaultTableModel model = new DefaultTableModel(rows, cols);

        // Bird starting position on the grid
        int birdRow = 14;
        int birdCol = 8;

        // Scale the bird to fit a 2x2 cell area (48x48 px)
        int birdWidth = 2 * cellSize;
        int birdHeight = 2 * cellSize;
        Image originalBird = new ImageIcon("assets/images/flappy-icon.png").getImage();

        BufferedImage bufferedBird = new BufferedImage(birdWidth, birdHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedBird.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalBird, 0, 0, birdWidth, birdHeight, null);
        g2d.dispose();

        // Split the bird image into 4 cells (upper-left, upper-right, lower-left, lower-right)
        ImageIcon upperLeftIcon = new ImageIcon(bufferedBird.getSubimage(0, 0, cellSize, cellSize));
        ImageIcon upperRightIcon = new ImageIcon(bufferedBird.getSubimage(cellSize, 0, cellSize, cellSize));
        ImageIcon lowerLeftIcon = new ImageIcon(bufferedBird.getSubimage(0, cellSize, cellSize, cellSize));
        ImageIcon lowerRightIcon = new ImageIcon(bufferedBird.getSubimage(cellSize, cellSize, cellSize, cellSize));

        // Load pipe image and put it in a BufferedImage so we can crop it
        Image originalPipe = new ImageIcon("assets/images/pipe.png").getImage();
        int srcWidth = originalPipe.getWidth(null);
        int srcHeight = originalPipe.getHeight(null);

        BufferedImage fullPipe = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gPipe = fullPipe.createGraphics();
        gPipe.drawImage(originalPipe, 0, 0, null);
        gPipe.dispose();

        // Crop out the empty sides of pipe.png and separate the top rim from the bottom body
        int cropX = 67;
        int cropW = 271;
        BufferedImage rawPipeTop = fullPipe.getSubimage(cropX, 2, cropW, 110);
        BufferedImage rawPipeBottom = fullPipe.getSubimage(cropX, 113, cropW, 395);

        int pipeCols = 3; // pipe is 3 cells wide
        int pipePixelWidth = pipeCols * cellSize;

        // Resize the pipe rim and body to match the width of 3 cells
        BufferedImage scaledPipeTop = new BufferedImage(pipePixelWidth, cellSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gTop = scaledPipeTop.createGraphics();
        gTop.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gTop.drawImage(rawPipeTop, 0, 0, pipePixelWidth, cellSize, null);
        gTop.dispose();

        BufferedImage scaledPipeBottom = new BufferedImage(pipePixelWidth, cellSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBot = scaledPipeBottom.createGraphics();
        gBot.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gBot.drawImage(rawPipeBottom, 0, 0, pipePixelWidth, cellSize, null);
        gBot.dispose();

        // Slice the pipe rim and body into 3 columns (left, middle, right)
        ImageIcon[] pipeTopIcons = new ImageIcon[pipeCols];
        ImageIcon[] pipeBottomIcons = new ImageIcon[pipeCols];
        for (int c = 0; c < pipeCols; c++) {
            pipeTopIcons[c] = new ImageIcon(scaledPipeTop.getSubimage(c * cellSize, 0, cellSize, cellSize));
            pipeBottomIcons[c] = new ImageIcon(scaledPipeBottom.getSubimage(c * cellSize, 0, cellSize, cellSize));
        }

        // Set pipe column and randomize heights (minimum size is 3x2 for both pipes)
        int pipeCol = 36;
        int minPipeHeight = 2;
        int gapRows = 8; // non-collision space between top and bottom pipes

        Random random = new Random();
        int maxTopHeight = rows - gapRows - minPipeHeight;
        int topPipeHeight = minPipeHeight + random.nextInt(maxTopHeight - minPipeHeight + 1);
        int bottomPipeStartRow = topPipeHeight + gapRows;

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

                // Draw the 4 parts of the bird
                if (row == birdRow && column == birdCol) {
                    cell.setIcon(upperLeftIcon);
                } else if (row == birdRow && column == birdCol + 1) {
                    cell.setIcon(upperRightIcon);
                } else if (row == birdRow + 1 && column == birdCol) {
                    cell.setIcon(lowerLeftIcon);
                } else if (row == birdRow + 1 && column == birdCol + 1) {
                    cell.setIcon(lowerRightIcon);
                }
                // Draw the top and bottom pipes
                else if (column >= pipeCol && column < pipeCol + pipeCols) {
                    int pipePartCol = column - pipeCol;

                    // Top pipe: body on top, rim right above the gap
                    if (row < topPipeHeight - 1) {
                        cell.setIcon(pipeBottomIcons[pipePartCol]);
                    } else if (row == topPipeHeight - 1) {
                        cell.setIcon(pipeTopIcons[pipePartCol]);
                    }
                    // Bottom pipe: rim right below the gap, body going down to the bottom
                    else if (row == bottomPipeStartRow) {
                        cell.setIcon(pipeTopIcons[pipePartCol]);
                    } else if (row > bottomPipeStartRow) {
                        cell.setIcon(pipeBottomIcons[pipePartCol]);
                    }
                }

                return cell;
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            TableGameScreen screen = new TableGameScreen();
            screen.setVisible(true);
        });

    }
}

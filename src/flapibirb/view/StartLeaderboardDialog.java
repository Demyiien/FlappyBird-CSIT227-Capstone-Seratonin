package flapibirb.view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.*;

public class StartLeaderboardDialog extends JDialog implements ActionListener {

    private JPanel pnlMain;
    private JPanel startPnl;
    private JPanel leaderboardPnl;
    private JTextField tfName;
    private JRadioButton birb1rb;
    private JRadioButton birb2rb;
    private JRadioButton birb3rb;
    private JRadioButton birb4rb;
    private JRadioButton birb5rb;
    private JButton playButton;
    private JLabel nameLabel;
    private JLabel birbChoiceLabel;
    private JLabel leaderboardLabel;
    private JTable leaderboardTbl;

    // Palette
    private static final Color CARD_CREAM = new Color(246, 243, 235);
    private static final Color BORDER_NAVY = new Color(36, 52, 92);
    private static final Color SELECT_GOLD_BG = new Color(255, 238, 175);
    private static final Color GOLD_BTN = new Color(247, 195, 37);
    private static final Color TBL_HEADER_BG = new Color(184, 196, 245);
    private static final Color ROW_EVEN = new Color(239, 236, 228);
    private static final Color ROW_ODD = new Color(230, 226, 216);

    // pnlMain bg & banner fields
    private final URL imgUrl = getClass().getResource("/blueBG.jpg");
    private final Image backgroundImage = (imgUrl != null) ? new ImageIcon(imgUrl).getImage() : null;
    private Font bannerFont;
    private BufferedImage cachedBannerImg;
    private BufferedImage cachedBannerBirdImg;

    // ButtonGroup so only one can be picked at a time
    private ButtonGroup birbGroup;

    // For file handling
    private static final String leaderboardFile = "leaderboard.txt";
    private static final int maxEntries = 100;

    public StartLeaderboardDialog() {
        // 1. Load FlappyBirdy.ttf for the big top sign and PressStart2P-Regular.ttf for all UI text
        bannerFont = loadCustomFont("/FlappyBirdy.ttf", 105f);
        Font titleFont = loadCustomFont("/PressStart2P-Regular.ttf", 18f);
        Font labelFont = loadCustomFont("/PressStart2P-Regular.ttf", 15f);
        Font buttonFont = loadCustomFont("/PressStart2P-Regular.ttf", 16f);
        Font inputFont = loadCustomFont("/PressStart2P-Regular.ttf", 13f);
        Font tableHeaderFont = loadCustomFont("/PressStart2P-Regular.ttf", 12f);
        Font tableBodyFont = loadCustomFont("/PressStart2P-Regular.ttf", 11f);

        // Pre-render the solid white "FlappyBirb" sign and bird sprite synchronously
        cachedBannerImg = createSolidFlappyBanner("FlappyBirb", bannerFont);
        cachedBannerBirdImg = loadSyncScaledImage("/flappy-icon.png", 68, 68);

        // 2. Initialize birbGroup ButtonGroup
        birbGroup = new ButtonGroup();
        birbGroup.add(birb1rb);
        birbGroup.add(birb2rb);
        birbGroup.add(birb3rb);
        birbGroup.add(birb4rb);
        birbGroup.add(birb5rb);

        // Setup birb radio buttons with custom badge icons
        setupBirbOption(birb1rb, "/flappy-icon.png", "/liyahBird.png");
        setupBirbOption(birb2rb, "/flappy-icon.png", "/liyahBird.png");
        setupBirbOption(birb3rb, "/flappy-icon.png", "/liyahBird.png");
        setupBirbOption(birb4rb, "/flappy-icon.png", "/liyahBird.png");
        setupBirbOption(birb5rb, "/flappy-icon.png", "/liyahBird.png");

        // 3. Add top padding so the big [FlappyBirb + Bird] banner sits cleanly above both panels
        pnlMain.setBorder(new EmptyBorder(125, 30, 25, 30));

        // 4. Style startPnl & leaderboardPnl with clean rounded corners
        styleCreamCardPanel(startPnl, 16);
        styleCreamCardPanel(leaderboardPnl, 10);

        // 5. Style labels and text field with PressStart2P
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(BORDER_NAVY);

        birbChoiceLabel.setFont(labelFont);
        birbChoiceLabel.setForeground(BORDER_NAVY);

        leaderboardLabel.setFont(titleFont);
        leaderboardLabel.setForeground(BORDER_NAVY);
        leaderboardLabel.setBorder(new EmptyBorder(0, 0, 12, 0));

        tfName.setFont(inputFont);
        tfName.setForeground(BORDER_NAVY);
        tfName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_NAVY, 2, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        // 6. Style playButton as gold pill button with PressStart2P
        playButton.setFont(buttonFont);
        playButton.setBackground(GOLD_BTN);
        playButton.setForeground(BORDER_NAVY);
        playButton.setFocusPainted(false);
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        playButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_NAVY, 2, true),
                new EmptyBorder(14, 24, 14, 24)
        ));

        // 7. Leaderboard table setup (Rank, Name, Score — all centered!)
        DefaultTableModel model = new DefaultTableModel(new String[]{"Rank", "Name", "Score"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        leaderboardTbl.setModel(model);

        // placeholder top 10
        model.addRow(new Object[]{1, "Alliyah", 20});
        model.addRow(new Object[]{2, "Alliyah", 20});
        model.addRow(new Object[]{3, "Alliyah", 20});
        model.addRow(new Object[]{4, "Alliyah", 20});
        model.addRow(new Object[]{5, "Alliyah", 20});
        model.addRow(new Object[]{6, "Alliyah", 20});
        model.addRow(new Object[]{7, "Alliyah", 20});
        model.addRow(new Object[]{8, "Alliyah", 20});
        model.addRow(new Object[]{9, "Alliyah", 20});
        model.addRow(new Object[]{10, "Alliyah", 20});

        loadTop10();

        leaderboardTbl.setFont(tableBodyFont);
        leaderboardTbl.setShowGrid(false);
        leaderboardTbl.setIntercellSpacing(new Dimension(0, 0));
        leaderboardTbl.setForeground(BORDER_NAVY);
        leaderboardTbl.setFillsViewportHeight(true);

        JTableHeader header = leaderboardTbl.getTableHeader();
        header.setBackground(TBL_HEADER_BG);
        header.setForeground(BORDER_NAVY);
        header.setFont(tableHeaderFont);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(tableBodyFont);
                lbl.setForeground(BORDER_NAVY);
                lbl.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        };

        for (int i = 0; i < leaderboardTbl.getColumnCount(); i++) {
            leaderboardTbl.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Stretch the 10 rows dynamically to fill 100% of the scroll pane height
        SwingUtilities.invokeLater(() -> {
            Container parent = leaderboardTbl.getParent();
            if (parent instanceof JViewport) {
                fitRowsToViewport((JViewport) parent);
                parent.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        fitRowsToViewport((JViewport) parent);
                    }
                });
            }
        });


        tfName.setName("tfName");
        birb1rb.setName("birb1rb");
        birb2rb.setName("birb2rb");
        birb3rb.setName("birb3rb");
        birb4rb.setName("birb4rb");
        birb5rb.setName("birb5rb");
        playButton.setName("playButton");
        nameLabel.setName("nameLabel");
        birbChoiceLabel.setName("birbChoiceLabel");
        leaderboardLabel.setName("leaderboardLabel");
        leaderboardTbl.setName("leaderboardTbl");

        playButton.addActionListener(this);
    }

    // File writing
    private void writeScore(String playerName, int score) {
        String cleanName = playerName.trim().replace(",", " ");
        java.util.List<String[]> entries = readAllFromFile();

        // Placeholder functions muna cos collision logic is wala pa
        // Adds names to the first index
        entries.add(0, new String[]{cleanName, String.valueOf(score)});

        // Not placeholder functions from here
        // Sorts scores
        entries.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        // Limit at 100 entries
        if (entries.size() > maxEntries) {
            entries = new ArrayList<>(entries.subList(0, maxEntries));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(leaderboardFile))) {
            for (String[] entry: entries) {
                bw.write(entry[0] + "," + entry[1]);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing leaderboard: " + e.getMessage());
        }

    }

    // File reading
    private List<String[]> readAllFromFile() {
        List<String[]> entries = new ArrayList<>();
        File file = new File(leaderboardFile);
        if (!file.exists()) {
            return entries;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(leaderboardFile))) {
            String line;
            while((line = br.readLine()) != null) {
                int comma = line.lastIndexOf(',');
                if (comma > 0) {
                    entries.add(new String[]{
                            line.substring(0, comma).trim(),
                            line.substring(comma + 1).trim()
                    });
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing leaderboard: " + e.getMessage());
        }

        return entries;
    }

    // Reads top 10
    private void loadTop10() {
        File file = new File(leaderboardFile);
        if (!file.exists()) {
            writeScore("Alliyah", 20);
        }
        DefaultTableModel model = (DefaultTableModel) leaderboardTbl.getModel();
        model.setRowCount(0);

        List<String[]> all = readAllFromFile();
        for (int i = 0; i < 10; i++) {
            if (i < all.size()) {
                model.addRow(new Object[]{i + 1, all.get(i)[0], Integer.parseInt(all.get(i)[1])});
            } else {
                model.addRow(new Object[]{i + 1, "", ""});
            }
        }

        Container parent = leaderboardTbl.getParent();
        if (parent instanceof JViewport) {
            fitRowsToViewport((JViewport) parent);
        }
    }

    private void setupBirbOption(JRadioButton rb, String defaultPath, String selectedPath) {
        BufferedImage defaultSprite = loadSyncScaledImage(defaultPath, 40, 40);
        BufferedImage selectedSprite = loadSyncScaledImage(selectedPath, 40, 40);

        rb.setText("");
        rb.setOpaque(false);
        rb.setContentAreaFilled(false);
        rb.setFocusPainted(false);
        rb.setBorder(new EmptyBorder(2, 2, 2, 2));
        rb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rb.setIcon(createBirbBadgeIcon(defaultSprite, false));
        rb.setSelectedIcon(createBirbBadgeIcon(selectedSprite != null ? selectedSprite : defaultSprite, true));
    }

    private Icon createBirbBadgeIcon(BufferedImage sprite, boolean selected) {
        int w = 52;
        int h = 60;
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int badgeY = y + 10;
                int badgeH = 48;

                if (selected) {
                    g2.setColor(SELECT_GOLD_BG);
                    g2.fillRoundRect(x + 1, badgeY, w - 2, badgeH, 14, 14);

                    g2.setColor(BORDER_NAVY);
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawRoundRect(x + 1, badgeY, w - 2, badgeH, 14, 14);

                    int midX = x + w / 2;
                    Polygon arrow = new Polygon(
                            new int[]{midX - 5, midX + 5, midX},
                            new int[]{y + 1, y + 1, y + 7},
                            3
                    );
                    g2.setColor(GOLD_BTN);
                    g2.fillPolygon(arrow);
                    g2.setColor(BORDER_NAVY);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawPolygon(arrow);
                } else if (c instanceof AbstractButton && ((AbstractButton) c).getModel().isRollover()) {
                    g2.setColor(new Color(36, 52, 92, 80));
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(x + 1, badgeY, w - 2, badgeH, 14, 14);
                }

                if (sprite != null) {
                    int spriteX = x + (w - sprite.getWidth()) / 2;
                    int spriteY = badgeY + (badgeH - sprite.getHeight()) / 2;
                    g2.drawImage(sprite, spriteX, spriteY, null);
                }

                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return w;
            }

            @Override
            public int getIconHeight() {
                return h;
            }
        };
    }

    private void fitRowsToViewport(JViewport viewport) {
        int rows = leaderboardTbl.getRowCount();
        int availableHeight = viewport.getHeight();
        if (rows > 0 && availableHeight > 0) {
            int rowHeight = Math.max(28, availableHeight / rows);
            leaderboardTbl.setRowHeight(rowHeight);
        }
    }

    private Font loadCustomFont(String path, float size) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException(path + " not found");
            }
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(baseFont);
            return baseFont.deriveFont(size);
        } catch (Exception e) {
            System.err.println("Error loading font " + path + ": " + e.getMessage());
            return new Font("Monospaced", Font.BOLD, (int) size);
        }
    }

    private BufferedImage loadSyncScaledImage(String path, int w, int h) {
        URL url = getClass().getResource(path);
        if (url == null) return null;

        Image img = new ImageIcon(url).getImage();
        if (img == null || img.getWidth(null) <= 0) return null;

        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return scaled;
    }

    private void styleCreamCardPanel(JPanel panel, int topInset) {
        panel.setOpaque(true);
        panel.setBackground(CARD_CREAM);
        panel.setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 26;
                Area outerCorners = new Area(new Rectangle2D.Float(0, 0, w, h));
                outerCorners.subtract(new Area(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, arc, arc)));

                if (backgroundImage != null && pnlMain != null && pnlMain.getWidth() > 0 && pnlMain.getHeight() > 0) {
                    Point offset = SwingUtilities.convertPoint(c, 0, 0, pnlMain);
                    g2.setClip(outerCorners);
                    g2.drawImage(backgroundImage, -offset.x, -offset.y, pnlMain.getWidth(), pnlMain.getHeight(), null);
                    g2.setClip(null);
                }

                g2.setColor(BORDER_NAVY);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawRoundRect(1, 1, w - 3, h - 3, arc, arc);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(topInset, 16, 16, 16);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == playButton) {
            String playerName = tfName.getText().trim();

            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name!", "Missing Name", JOptionPane.WARNING_MESSAGE);
                tfName.requestFocus();
                return;
            }

            writeScore(playerName, 20);
            loadTop10();
            dispose();
        }
    }

    private void createUIComponents() {
        pnlMain = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }

                if (cachedBannerImg == null) {
                    Font font = (bannerFont != null) ? bannerFont : new Font("Monospaced", Font.BOLD, 105);
                    cachedBannerImg = createSolidFlappyBanner("FlappyBirb", font);
                }

                if (cachedBannerBirdImg == null) {
                    cachedBannerBirdImg = loadSyncScaledImage("/flappy-icon.png", 68, 68);
                }

                int gap = 16;
                int birdW = (cachedBannerBirdImg != null) ? cachedBannerBirdImg.getWidth() : 0;
                int birdH = (cachedBannerBirdImg != null) ? cachedBannerBirdImg.getHeight() : 0;
                int totalWidth = cachedBannerImg.getWidth() + (birdW > 0 ? gap + birdW : 0);

                int startX = (getWidth() - totalWidth) / 2;
                int bannerY = 12;

                // Draw "FlappyBirb" on the left, and the bird icon on the right
                g.drawImage(cachedBannerImg, startX, bannerY, null);
                if (cachedBannerBirdImg != null) {
                    int birdY = bannerY + (cachedBannerImg.getHeight() - birdH) / 2 - 2;
                    g.drawImage(cachedBannerBirdImg, startX + cachedBannerImg.getWidth() + gap, birdY, null);
                }
            }
        };
    }

    private BufferedImage createSolidFlappyBanner(String text, Font font) {
        BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gDummy = dummy.createGraphics();
        gDummy.setFont(font);
        FontMetrics fm = gDummy.getFontMetrics();
        int pad = 20;
        int w = fm.stringWidth(text) + pad * 2;
        int h = fm.getHeight() + pad * 2;
        int baselineY = pad + fm.getAscent();
        gDummy.dispose();

        // 1. Draw the raw font outlines (1px dark lines)
        BufferedImage outlineImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gOut = outlineImg.createGraphics();
        gOut.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        gOut.setFont(font);
        gOut.setColor(new Color(55, 48, 56));
        gOut.drawString(text, pad, baselineY);
        gOut.dispose();

        // 2. Flood-fill from (0,0) to mark all exterior background pixels
        boolean[][] outside = new boolean[w][h];
        ArrayDeque<Point> queue = new ArrayDeque<>();
        queue.add(new Point(0, 0));
        outside[0][0] = true;

        int[] dirX = {1, -1, 0, 0};
        int[] dirY = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = p.x + dirX[i];
                int ny = p.y + dirY[i];
                if (nx >= 0 && nx < w && ny >= 0 && ny < h && !outside[nx][ny]) {
                    if (((outlineImg.getRGB(nx, ny) >>> 24) & 0xFF) == 0) {
                        outside[nx][ny] = true;
                        queue.add(new Point(nx, ny));
                    }
                }
            }
        }

        // 3. Create solid white interior mask
        BufferedImage whiteInterior = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        int whiteRGB = Color.WHITE.getRGB();
        for (int px = 0; px < w; px++) {
            for (int py = 0; py < h; py++) {
                if (!outside[px][py]) {
                    whiteInterior.setRGB(px, py, whiteRGB);
                }
            }
        }

        // 4. Composite in exact layer order
        BufferedImage finalImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = finalImg.createGraphics();

        // Layer A (Back): Thin bottom-only drop-shadow
        BufferedImage darkShadow = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        int darkRGB = new Color(55, 48, 56).getRGB();
        for (int px = 0; px < w; px++) {
            for (int py = 0; py < h; py++) {
                if (!outside[px][py]) {
                    darkShadow.setRGB(px, py, darkRGB);
                }
            }
        }
        for (int dy = 1; dy <= 5; dy++) {
            g2.drawImage(darkShadow, 0, dy, null);
        }

        // Layer B (Middle): Solid white fill inside the letters
        g2.drawImage(whiteInterior, 0, 0, null);

        // Layer C (Front): Crisp dark inner dividing lines separating each letter
        g2.drawImage(outlineImg, 0, 0, null);

        g2.dispose();
        return finalImg;
    }

    public static void main(String[] args) {
        StartLeaderboardDialog app = new StartLeaderboardDialog();
        app.setContentPane(app.pnlMain);
        app.setTitle("Flappy Birb");
        app.pack();
        app.setLocationRelativeTo(null);
        app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        app.setVisible(true);
    }
}
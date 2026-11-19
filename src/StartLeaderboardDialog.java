import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.imageio.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;
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

    // pnlMain bg fields
    private final URL imgUrl = getClass().getResource("/blueBG.jpg");
    private final Image backgroundImage = (imgUrl != null) ? new ImageIcon(imgUrl).getImage() : null;

    // ButtonGroup so only one can be picked at a time
    private ButtonGroup birbGroup;

    public StartLeaderboardDialog() {

        // adding birb radioButtons to birbGroup ButtonGroup
        birbGroup = new ButtonGroup();
        birbGroup.add(birb1rb);
        birbGroup.add(birb2rb);
        birbGroup.add(birb3rb);
        birbGroup.add(birb4rb);
        birbGroup.add(birb5rb);
        birb1rb.setSelected(true);

        // changing birb1 icon to photo:
        try {
            // load & resize the UNSELECTED image
            URL url1 = getClass().getResource("/flappy-icon.png");
            if (url1 == null) {
                throw new FileNotFoundException("flappy-icon.png not found");
            }
            ImageIcon originalbirb1Icon = new ImageIcon(url1);
            Image scaledImage1 = originalbirb1Icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb1rb.setIcon(new ImageIcon(scaledImage1));

            // load & resize the SELECTED image
            URL selectedUrl1 = getClass().getResource("/liyahBird.png");
            if (selectedUrl1 == null) {
                throw new FileNotFoundException("liyahBird.png not found");
            }
            ImageIcon originalSelected1 = new ImageIcon(selectedUrl1);
            Image scaledSelected1 = originalSelected1.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb1rb.setSelectedIcon(new ImageIcon(scaledSelected1));
        } catch (Exception e) {
            System.err.println("Error loading birb1: " + e.getMessage());
        }
        birb1rb.setText("");
        birb1rb.setOpaque(false);

        // changing birb2 icon to photo:
        try {
            // load & resize the UNSELECTED image
            URL url2 = getClass().getResource("/flappy-icon.png");
            if (url2 == null) {
                throw new FileNotFoundException("flappy-icon.png not found");
            }
            ImageIcon originalbirb2Icon = new ImageIcon(url2);
            Image scaledImage2 = originalbirb2Icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb2rb.setIcon(new ImageIcon(scaledImage2));

            // load & resize the SELECTED image
            URL selectedUrl2 = getClass().getResource("/liyahBird.png");
            if (selectedUrl2 == null) {
                throw new FileNotFoundException("liyahBird.png not found");
            }
            ImageIcon originalSelected2 = new ImageIcon(selectedUrl2);
            Image scaledSelected2 = originalSelected2.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb2rb.setSelectedIcon(new ImageIcon(scaledSelected2));

        } catch (Exception e) {
            System.err.println("Error loading birb2: " + e.getMessage());
        }
        birb2rb.setText("");
        birb2rb.setOpaque(false);

        // changing birb3 icon to photo:
        try {
            // load & resize the UNSELECTED image
            URL url3 = getClass().getResource("/flappy-icon.png");
            if (url3 == null) {
                throw new FileNotFoundException("flappy-icon.png not found");
            }
            ImageIcon originalbirb3Icon = new ImageIcon(url3);
            Image scaledImage3 = originalbirb3Icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb3rb.setIcon(new ImageIcon(scaledImage3));

            // load & resize the SELECTED image
            URL selectedUrl3 = getClass().getResource("/liyahBird.png");
            if (selectedUrl3 == null) {
                throw new FileNotFoundException("liyahBird.png not found");
            }
            ImageIcon originalSelected3 = new ImageIcon(selectedUrl3);
            Image scaledSelected3 = originalSelected3.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb3rb.setSelectedIcon(new ImageIcon(scaledSelected3));

        } catch (Exception e) {
            System.err.println("Error loading birb3: " + e.getMessage());
        }
        birb3rb.setText("");
        birb3rb.setOpaque(false);

        // changing birb4 icon to photo:
        try {
            // load & resize the UNSELECTED image
            URL url4 = getClass().getResource("/flappy-icon.png");
            if (url4 == null) {
                throw new FileNotFoundException("flappy-icon.png not found");
            }
            ImageIcon originalbirb4Icon = new ImageIcon(url4);
            Image scaledImage4 = originalbirb4Icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb4rb.setIcon(new ImageIcon(scaledImage4));

            // load & resize the SELECTED image
            URL selectedUrl4 = getClass().getResource("/liyahBird.png");
            if (selectedUrl4 == null) {
                throw new FileNotFoundException("liyahBird.png not found");
            }
            ImageIcon originalSelected4 = new ImageIcon(selectedUrl4);
            Image scaledSelected4 = originalSelected4.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb4rb.setSelectedIcon(new ImageIcon(scaledSelected4));

        } catch (Exception e) {
            System.err.println("Error loading birb4: " + e.getMessage());
        }
        birb4rb.setText("");
        birb4rb.setOpaque(false);

        // changing birb5 icon to photo:
        try {
            // load & resize the UNSELECTED image
            URL url5 = getClass().getResource("/flappy-icon.png");
            if (url5 == null) {
                throw new FileNotFoundException("flappy-icon.png not found");
            }
            ImageIcon originalbirb5Icon = new ImageIcon(url5);
            Image scaledImage5 = originalbirb5Icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb5rb.setIcon(new ImageIcon(scaledImage5));

            // load & resize the SELECTED image
            URL selectedUrl5 = getClass().getResource("/liyahBird.png");
            if (selectedUrl5 == null) {
                throw new FileNotFoundException("liyahBird.png not found");
            }
            ImageIcon originalSelected5 = new ImageIcon(selectedUrl5);
            Image scaledSelected5 = originalSelected5.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            birb5rb.setSelectedIcon(new ImageIcon(scaledSelected5));

        } catch (Exception e) {
            System.err.println("Error loading birb5: " + e.getMessage());
        }
        birb5rb.setText("");
        birb5rb.setOpaque(false);


        // round borders for startPnl
        startPnl.setOpaque(false);
        startPnl.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                // temporary Graphics2D copy so our settings (color/stroke) don't bleed into other UI elements
                Graphics2D g2 = (Graphics2D) g.create();
                // enable Anti-Aliasing to smooth out pixelated edges on the rounded corners
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // fill rounded bg
                g2.setColor(new Color(56,113,225));
                g2.fillRoundRect(0, 0, w - 1, h - 1, 20, 20);

                // draw border
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, w - 2, h - 2, 20, 20);

                // release system graphics resources immediately after drawing
                g2.dispose();

            }

            @Override
            public Insets getBorderInsets(Component c) {
                 return new Insets(10, 10, 10, 10);

            }
        });


        // round borders for leaderboard
        leaderboardPnl.setOpaque(false);
        leaderboardPnl.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(56,113,225));
                g2.fillRoundRect(0, 0, w - 1, h - 1, 20, 20);

                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, w - 2, h - 2, 20, 20);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(10, 10, 10, 10);
            }

        });

        // edit playButton label
        playButton.setFont(new Font("JetBrains Mono ExtraLight", Font.BOLD, 18));

        // leaderboard table stuff
        DefaultTableModel birbTablemodel = (DefaultTableModel) leaderboardTbl.getModel();
        birbTablemodel.addColumn("Rank");
        birbTablemodel.addColumn("Name");
        birbTablemodel.addColumn("Score");
        birbTablemodel.addColumn("Birb");

        loadLeaderboard(birbTablemodel);

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

    public String getPlayerName() {
        String playerName = tfName.getText().trim();
        return playerName;
    }

    public String getSelectedBirb() {
        if (birb2rb.isSelected()) return "Birb 2";
        if (birb3rb.isSelected()) return "Birb 3";
        if (birb4rb.isSelected()) return "Birb 4";
        if (birb5rb.isSelected()) return "Birb 5";
        return "Birb 1";

    }


    private void loadLeaderboard(DefaultTableModel model) {
        File file = new File("leaderboard.txt");
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int rank = 1;

            while ((line = br.readLine()) != null && rank <= 10) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    model.addRow(new Object[] {
                            rank++,
                            parts[0].trim(),
                            Integer.parseInt(parts[1].trim()),
                            parts[2].trim()
                   });
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.err.print("Error loading leaderboard: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == playButton) {

            if (getPlayerName().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name!", "Missing Name", JOptionPane.WARNING_MESSAGE);
                tfName.requestFocus();
                return;
            }
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
            }
        };
    }

    public static void main(String[] args) {
        StartLeaderboardDialog app = new StartLeaderboardDialog();
        app.setContentPane(app.pnlMain);
        app.setTitle("Flappy Birb");
        app.pack();
        app.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        app.setVisible(true);
    }
}

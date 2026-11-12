import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

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
    private JTable lbLabel;

    // pnlMain bg fields
    private final URL imgUrl = getClass().getResource("/blueBG.jpg");
    private final Image backgroundImage = (imgUrl != null) ? new ImageIcon(imgUrl).getImage() : null;

    // ButtonGroup so only one can be picked at a time
    private ButtonGroup birbGroup;

    public StartLeaderboardDialog() {

        // adding birb radioButtons to birbGroup ButtonGroup
        ButtonGroup birbGroup = new ButtonGroup();
        birbGroup.add(birb1rb);
        birbGroup.add(birb2rb);
        birbGroup.add(birb3rb);
        birbGroup.add(birb4rb);
        birbGroup.add(birb5rb);

        // changing birb1 icon to photo:
        // load the image
        ImageIcon originalbirb1Icon = new ImageIcon(getClass().getResource("/flappy-icon.png"));

        // resize image
        Image scaledImage1 = originalbirb1Icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon birb1Icon = new ImageIcon(scaledImage1);

        // liyah unsure pt. 1: what happens when a birb is selected? i dono so comment ka muna makalimot nya ko syntax
        // ImageIcon birb1Selected = new ImageIcon(getClass().getResource("/birb1_selected.png"));

        // apply icons and remove default radio button graphics
        birb1rb.setIcon(birb1Icon);
        // liyah unsure pt. 2: still dono what to put here
        // birb1rb.setSelectedIcon(birb1Selected);
        birb1rb.setText("");                      // remove default label text
        birb1rb.setOpaque(false);                 // remove background box

        // changing birb2 icon to photo:
        ImageIcon originalbirb2Icon = new ImageIcon(getClass().getResource("/liyahBird.png"));
        Image scaledImage2 = originalbirb2Icon.getImage().getScaledInstance(40, 40  , Image.SCALE_SMOOTH);
        ImageIcon birb2Icon = new ImageIcon(scaledImage2);
        birb2rb.setIcon(birb2Icon);
        birb2rb.setText("");
        birb2rb.setOpaque(false);

        // changing birb3 icon to photo:
        ImageIcon originalbirb3Icon = new ImageIcon(getClass().getResource("/tjParrot.png"));
        Image scaledImage3 = originalbirb3Icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        ImageIcon birb3Icon = new ImageIcon(scaledImage3);
        birb3rb.setIcon(birb3Icon);
        birb3rb.setText("");
        birb3rb.setOpaque(false);

        // changing birb4 icon to photo:
        ImageIcon originalbirb4Icon = new ImageIcon(getClass().getResource("/flappy-icon.png"));
        Image scaledImage4 = originalbirb4Icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon birb4Icon = new ImageIcon(scaledImage4);
        birb4rb.setIcon(birb4Icon);
        birb4rb.setText("");
        birb4rb.setOpaque(false);

        // changing birb5 icon to photo:
        ImageIcon originalbirb5Icon = new ImageIcon(getClass().getResource("/flappy-icon.png"));
        Image scaledImage5 = originalbirb4Icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon birb5Icon = new ImageIcon(scaledImage5);
        birb5rb.setIcon(birb5Icon);
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

                // draw border line
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
        lbLabel.setName("lbLabel");

        playButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {


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
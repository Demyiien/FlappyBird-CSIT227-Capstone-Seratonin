import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableGameScreen extends JFrame {

    private JTable table1;
    private JPanel panel1;
    private JScrollPane scrollPane;

    public TableGameScreen() {

        panel1 = new JPanel(new GridBagLayout());
        panel1.setBackground(Color.PINK);

        DefaultTableModel model = new DefaultTableModel(64, 128);

        table1 = new JTable(model);

        table1.setRowHeight(12);

        for (int i = 0; i < 128; i++) {
            table1.getColumnModel().getColumn(i).setPreferredWidth(12);
            table1.getColumnModel().getColumn(i).setMinWidth(12);
            table1.getColumnModel().getColumn(i).setMaxWidth(12);
        }

        table1.setIntercellSpacing(new Dimension(0, 0));
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setShowGrid(true);
        table1.setGridColor(Color.GRAY);
        table1.setTableHeader(null);

        Color alphaZero = new Color(0, 0, 0, 0);
        table1.setOpaque(false);
        table1.setBackground(alphaZero);

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(alphaZero);

        table1.setPreferredScrollableViewportSize(new Dimension(768, 768));

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

                Component cell = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                cell.setBackground(Color.GREEN);
                setBorder(null);

                if (row == 5 && column == 10) {
                    cell.setBackground(Color.RED);
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
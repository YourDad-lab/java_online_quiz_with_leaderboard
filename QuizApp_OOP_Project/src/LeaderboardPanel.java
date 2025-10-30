import javax.swing.*;
import java.awt.*;

public class LeaderboardPanel extends JPanel {
    public LeaderboardPanel(CardLayout cl, JPanel mp) {
        setLayout(new BorderLayout());
        String[] cols = {"Rank", "Username", "Score", "Date"};
        String[][] data = DatabaseConnection.getLeaderboard();
        for (int i = 0; i < data.length; i++) {
            String[] row = new String[4];
            row[0] = String.valueOf(i+1);
            System.arraycopy(data[i], 0, row, 1, 3);
            data[i] = row;
        }
        JTable table = new JTable(data, cols);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> cl.show(mp, "Menu"));
        add(back, BorderLayout.SOUTH);
    }
}
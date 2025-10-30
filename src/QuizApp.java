import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class QuizApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { DatabaseConnection.getConnection().close(); }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "MySQL Connection Failed!\n" + e.getMessage());
                System.exit(1);
            }

            JFrame f = new JFrame("Online Quiz System with Leaderboard");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(700, 500);
            f.setLocationRelativeTo(null);

            JPanel main = new JPanel();
            CardLayout cl = new CardLayout();
            main.setLayout(cl);

            main.add(new LoginPanel(cl, main), "Login");
            f.add(main);
            f.setVisible(true);
        });
    }
}
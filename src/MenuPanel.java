import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public MenuPanel(String username, CardLayout cl, JPanel mp) {
        setLayout(new GridLayout(5, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        add(new JLabel("Welcome, " + username + "!", SwingConstants.CENTER));
        JButton quizBtn = new JButton("Start Quiz");
        JButton leadBtn = new JButton("View Leaderboard");
        JButton logoutBtn = new JButton("Logout");

        quizBtn.addActionListener(e -> {
            mp.add(new QuizPanel(username, cl, mp), "Quiz");
            cl.show(mp, "Quiz");
        });
        leadBtn.addActionListener(e -> {
            mp.add(new LeaderboardPanel(cl, mp), "Leaderboard");
            cl.show(mp, "Leaderboard");
        });
        logoutBtn.addActionListener(e -> cl.show(mp, "Login"));

        add(quizBtn); add(leadBtn); add(logoutBtn);
        add(new JLabel(""));
    }
}
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuizPanel extends JPanel {
    private List<Question> questions = new ArrayList<>();
    private int index = 0, score = 0;
    private String username;
    private CardLayout cl; private JPanel mp;
    private JLabel qLabel; private JRadioButton[] opts; private ButtonGroup group; private JButton nextBtn;

    public QuizPanel(String user, CardLayout card, JPanel main) {
        this.username = user; this.cl = card; this.mp = main;
        setLayout(new BorderLayout(10, 10));
        loadQuestions();

        qLabel = new JLabel(); qLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        opts = new JRadioButton[4]; group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            opts[i] = new JRadioButton(); group.add(opts[i]);
        }

        JPanel optPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        for (JRadioButton rb : opts) optPanel.add(rb);

        nextBtn = new JButton("Next");
        nextBtn.addActionListener(e -> nextQuestion());

        add(qLabel, BorderLayout.NORTH);
        add(optPanel, BorderLayout.CENTER);
        add(nextBtn, BorderLayout.SOUTH);

        showQuestion();
    }

    private void loadQuestions() {
        questions.add(new Question("Capital of India?", new String[]{"Delhi", "Mumbai", "Kolkata", "Chennai"}, 0));
        questions.add(new Question("2 + 2 = ?", new String[]{"3", "4", "5", "6"}, 1));
        questions.add(new Question("Largest planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 2));
        questions.add(new Question("Java is ___", new String[]{"Low-level", "Mid-level", "High-level", "Script"}, 2));
        questions.add(new Question("OOP stands for?", new String[]{"Object Oriented Programming", "Order of Operations", "Open Office", "None"}, 0));
        questions.add(new Question("SQL is used for?", new String[]{"Styling", "Database", "Logic", "Animation"}, 1));
        questions.add(new Question("Swing is part of?", new String[]{"AWT", "JFC", "JDK", "All"}, 1));
        questions.add(new Question("HashMap allows?", new String[]{"Duplicates", "Null key", "Order", "None"}, 1));
        questions.add(new Question("JDBC stands for?", new String[]{"Java Database Connectivity", "Java Data Control", "None"}, 0));
        questions.add(new Question("GUI means?", new String[]{"Graphical User Interface", "General User Input", "None"}, 0));
    }

    private void showQuestion() {
        if (index >= questions.size()) { endQuiz(); return; }
        Question q = questions.get(index);
        qLabel.setText("<html><b>Q" + (index+1) + ":</b> " + q.getText() + "</html>");
        String[] o = q.getOptions();
        for (int i = 0; i < 4; i++) {
            opts[i].setText(o[i]);
            opts[i].setSelected(false);
        }
        nextBtn.setText(index == questions.size()-1 ? "Submit" : "Next");
        group.clearSelection();
    }

    private void nextQuestion() {
        Question q = questions.get(index);
        for (int i = 0; i < 4; i++) {
            if (opts[i].isSelected() && q.isCorrect(i)) score++;
        }
        index++;
        showQuestion();
    }

    private void endQuiz() {
        DatabaseConnection.submitScore(username, score);
        JOptionPane.showMessageDialog(this, "Quiz Over!\nScore: " + score + "/10");
        cl.show(mp, "Menu");
    }
}
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField userField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public LoginPanel(CardLayout cl, JPanel mp) {
        this.cardLayout = cl;
        this.mainPanel = mp;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("ONLINE QUIZ SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridwidth = 2; gbc.gridy = 0; add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; add(new JLabel("Username:"), gbc);
        gbc.gridy = 2; add(userField, gbc);
        gbc.gridy = 3; add(new JLabel("Password:"), gbc);
        gbc.gridy = 4; add(passField, gbc);

        JPanel btnPanel = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");
        btnPanel.add(loginBtn); btnPanel.add(regBtn);
        gbc.gridwidth = 2; gbc.gridy = 5; add(btnPanel, gbc);

        loginBtn.addActionListener(e -> login());
        regBtn.addActionListener(e -> register());
    }

    private void login() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }
        if (DatabaseConnection.loginUser(user, pass)) {
            mainPanel.add(new MenuPanel(user, cardLayout, mainPanel), "Menu");
            cardLayout.show(mainPanel, "Menu");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    private void register() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }
        if (DatabaseConnection.registerUser(user, pass)) {
            JOptionPane.showMessageDialog(this, "Registered! Now login.");
        } else {
            JOptionPane.showMessageDialog(this, "Username exists!");
        }
    }
}
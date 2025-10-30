import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "meera77#";  // Change if needed

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing error", e);
        }
    }

    public static boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean loginUser(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(hashPassword(password));
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void submitScore(String username, int score) {
        String query = "INSERT INTO scores (username, score) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[][] getLeaderboard() {
        String query = "SELECT username, score, quiz_date FROM scores ORDER BY score DESC, quiz_date ASC LIMIT 10";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            java.util.List<String[]> list = new java.util.ArrayList<>();
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("username"),
                    String.valueOf(rs.getInt("score")),
                    rs.getTimestamp("quiz_date").toString().substring(0, 19)
                });
            }
            return list.toArray(new String[0][]);
        } catch (SQLException e) {
            return new String[0][];
        }
    }
}
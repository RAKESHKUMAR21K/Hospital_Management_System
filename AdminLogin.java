import java.sql.*;
import java.util.Scanner;

public class AdminLogin {

    public static boolean login(String username, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM Admin WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true; // Login successful
            } else {
                return false; // Invalid credentials
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }
}

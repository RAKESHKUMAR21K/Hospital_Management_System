import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/HospitalManagement";  // Your database name
            String user = "root";  // Your database username
            String password = "@Rakesh7876";  // Your database password
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }
}

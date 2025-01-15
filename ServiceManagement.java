import java.sql.*;
import java.util.Scanner;

public class ServiceManagement {

    public static void serviceMenu() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Service Management ===");
            System.out.println("1. Add Service");
            System.out.println("2. View Services");
            System.out.println("3. Update Service");
            System.out.println("4. Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // Add a new service
                    addService();
                    break;
                case 2:
                    // View service records
                    viewServices();
                    break;
                case 3:
                    // Update service details
                    updateService();
                    break;
                case 4:
                    // Go back to main menu
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Add a service
    public static void addService() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter service name: ");
        String serviceName = sc.nextLine();  // Change to nextLine for multi-word names
        System.out.print("Enter service cost: ");
        double cost = sc.nextDouble();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO Services (service_name, cost) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, serviceName);
            stmt.setDouble(2, cost);
            stmt.executeUpdate();
            System.out.println("Service added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding service: " + e.getMessage());
        }
    }

    // View all services
    public static void viewServices() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Services")) {
    
            if (!rs.isBeforeFirst()) {
                System.out.println("No services found.");
                return;
            }
    
            // Print table headers
            System.out.printf("%-15s %-30s %-10s%n", 
                              "Service ID", "Service Name", "Service Cost");
            System.out.println("-------------------------------------------------------------");
    
            // Print table rows
            while (rs.next()) {
                System.out.printf("%-15d %-30s %-10.2f%n",
                                  rs.getInt("service_id"),
                                  rs.getString("service_name"),
                                  rs.getDouble("cost"));
            }
    
        } catch (SQLException e) {
            System.out.println("Error viewing services: " + e.getMessage());
        }
    }
    

    // Update a service
    public static void updateService() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter service ID to update: ");
        int serviceId = sc.nextInt();
        sc.nextLine();  // Consume the newline character left by nextInt()
        System.out.print("Enter new service name: ");
        String serviceName = sc.nextLine();
        System.out.print("Enter new service cost: ");
        double cost = sc.nextDouble();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE Services SET service_name = ?, cost = ? WHERE service_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, serviceName);
            stmt.setDouble(2, cost);
            stmt.setInt(3, serviceId);
            stmt.executeUpdate();
            System.out.println("Service updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating service: " + e.getMessage());
        }
    }
}

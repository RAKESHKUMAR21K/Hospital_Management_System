import java.sql.*;
import java.util.Scanner;

public class BillingManagement {

    public static void billingMenu() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Billing Management ===");
            System.out.println("1. Generate Bill");
            System.out.println("2. View Bills");
            System.out.println("3. Update Payment Status");
            System.out.println("4. Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // Generate bill for an appointment
                    generateBill();
                    break;
                case 2:
                    // View all bills
                    viewBills();
                    break;
                case 3:
                    // Update payment status
                    updatePaymentStatus();
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

    public static void generateBill() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter appointment ID to generate bill: ");
        int appointmentId = sc.nextInt();
        System.out.print("Enter total amount: ");
        double amount = sc.nextDouble();
        System.out.print("Enter payment status (Paid/Unpaid): ");
        String paymentStatus = sc.next();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO Billing (appointment_id, total_amount, payment_status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, appointmentId);
            stmt.setDouble(2, amount);
            stmt.setString(3, paymentStatus);
            stmt.executeUpdate();
            System.out.println("Bill generated successfully!");
        } catch (SQLException e) {
            System.out.println("Error generating bill: " + e.getMessage());
        }
    }

    public static void viewBills() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Billing")) {
    
            // Check if there are any records
            if (!rs.isBeforeFirst()) { // Checks if the result set is empty
                System.out.println("No bills found.");
                return;
            }
    
            // Print table headers
            System.out.printf("%-10s %-15s %-15s %-20s%n",
                    "Bill ID", "Appointment ID", "Total Amount", "Payment Status");
            System.out.println(
                    "------------------------------------------------------------");
    
            // Print table rows
            while (rs.next()) {
                System.out.printf("%-10d %-15d %-15.2f %-20s%n",
                        rs.getInt("bill_id"),
                        rs.getInt("appointment_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("payment_status"));
            }
    
        } catch (SQLException e) {
            System.out.println("Error viewing bills: " + e.getMessage());
        }
    }
    
    public static void updatePaymentStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter bill ID to update payment status: ");
        int billId = sc.nextInt();
        System.out.print("Enter new payment status (Paid/Unpaid): ");
        String paymentStatus = sc.next();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE Billing SET payment_status = ? WHERE bill_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, paymentStatus);
            stmt.setInt(2, billId);
            stmt.executeUpdate();
            System.out.println("Payment status updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating payment status: " + e.getMessage());
        }
    }
}

import java.sql.*;
import java.util.Scanner;

public class AppointmentManagement {

    public static void appointmentMenu() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Appointment Management ===");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Update Appointment Status");
            System.out.println("4. Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // Schedule a new appointment
                    scheduleAppointment();
                    break;
                case 2:
                    // View all appointments
                    viewAppointments();
                    break;
                case 3:
                    // Update appointment status
                    updateAppointmentStatus();
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

    public static void scheduleAppointment() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter patient ID: ");
        int patientId = sc.nextInt();
        System.out.print("Enter doctor ID: ");
        int doctorId = sc.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = sc.next();
        System.out.print("Enter appointment time (HH:MM:SS): ");
        String time = sc.next();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setString(5, "Scheduled");
            stmt.executeUpdate();
            System.out.println("Appointment scheduled successfully!");
        } catch (SQLException e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    public static void viewAppointments() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Appointments")) {
    
            // Check if there are any records
            if (!rs.isBeforeFirst()) { // Checks if the result set is empty
                System.out.println("No appointments found.");
                return;
            }
    
            // Print table headers
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n",
                    "Appointment ID", "Patient ID", "Doctor ID", "Date", "Time", "Status");
            System.out.println(
                    "-------------------------------------------------------------------------------");
    
            // Print each row
            while (rs.next()) {
                System.out.printf("%-15d %-15d %-15d %-15s %-15s %-15s%n",
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date"),
                        rs.getTime("appointment_time"),
                        rs.getString("status"));
            }
    
        } catch (SQLException e) {
            System.out.println("Error viewing appointments: " + e.getMessage());
        }
    }
    

    public static void updateAppointmentStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter appointment ID to update status: ");
        int appointmentId = sc.nextInt();
        System.out.print("Enter new status (e.g., Completed, Canceled): ");
        String status = sc.next();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE Appointments SET status = ? WHERE appointment_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setInt(2, appointmentId);
            stmt.executeUpdate();
            System.out.println("Appointment status updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating appointment status: " + e.getMessage());
        }
    }
}

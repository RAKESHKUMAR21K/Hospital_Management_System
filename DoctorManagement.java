import java.sql.*;
import java.util.Scanner;

public class DoctorManagement {

    public static void doctorMenu() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Doctor Management ===");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Go Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Add a new doctor
                        addDoctor(sc);
                        break;
                    case 2:
                        // View doctor records
                        viewDoctors();
                        break;
                    case 3:
                        // Update doctor information
                        updateDoctor(sc);
                        break;
                    case 4:
                        // Delete doctor record
                        deleteDoctor(sc);
                        break;
                    case 5:
                        // Go back to main menu
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Clear invalid input
            }
        }
    }

    public static void addDoctor(Scanner sc) {
        System.out.print("Enter doctor name: ");
        String name = sc.nextLine();
        System.out.print("Enter doctor specialization: ");
        String specialty = sc.nextLine();
        System.out.print("Enter doctor contact number: ");
        String contactNumber = sc.nextLine();
        System.out.print("Enter doctor email: ");
        String email = sc.nextLine();

        // Validate inputs
        if (name.isEmpty() || specialty.isEmpty() || contactNumber.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required. Please try again.");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO Doctors (name, specialty, contact, email) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, specialty);
            stmt.setString(3, contactNumber);
            stmt.setString(4, email);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Doctor added successfully!");
            } else {
                System.out.println("Failed to add doctor. Please try again.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding doctor: " + e.getMessage());
        }
    }

    public static void viewDoctors() {
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Doctors")) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No doctors found.");
                return;
            }

            // Print table headers
            System.out.printf("%-10s %-20s %-30s %-15s %-25s%n",
                    "Doctor ID", "Name", "Specialization", "Contact", "Email");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");

            // Print table rows
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-30s %-15s %-25s%n",
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialty"),
                        rs.getString("contact"),
                        rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("Error viewing doctors: " + e.getMessage());
        }
    }

    public static void updateDoctor(Scanner sc) {
        System.out.print("Enter doctor ID to update: ");
        int doctorId = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Enter new doctor name: ");
        String name = sc.nextLine();
        System.out.print("Enter new doctor specialization: ");
        String specialty = sc.nextLine();
        System.out.print("Enter new doctor contact number: ");
        String contactNumber = sc.nextLine();
        System.out.print("Enter new doctor email: ");
        String email = sc.nextLine();

        // Validate inputs
        if (name.isEmpty() || specialty.isEmpty() || contactNumber.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required. Please try again.");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE Doctors SET name = ?, specialty = ?, contact = ?, email = ? WHERE doctor_id = ?")) {

            stmt.setString(1, name);
            stmt.setString(2, specialty);
            stmt.setString(3, contactNumber);
            stmt.setString(4, email);
            stmt.setInt(5, doctorId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Doctor updated successfully!");
            } else {
                System.out.println("Doctor not found or update failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating doctor: " + e.getMessage());
        }
    }

    public static void deleteDoctor(Scanner sc) {
        System.out.print("Enter doctor ID to delete: ");
        int doctorId = sc.nextInt();
        sc.nextLine(); // Consume newline

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM Doctors WHERE doctor_id = ?")) {

            stmt.setInt(1, doctorId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Doctor deleted successfully!");
            } else {
                System.out.println("Doctor not found or deletion failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting doctor: " + e.getMessage());
        }
    }
}

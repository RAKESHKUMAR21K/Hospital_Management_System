import java.sql.*;
import java.util.Scanner;

public class PatientManagement {

    public static void patientMenu() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Patient Management ===");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // Add a new patient
                    addPatient();
                    break;
                case 2:
                    // View patient records
                    viewPatients();
                    break;
                case 3:
                    // Update patient information
                    updatePatient();
                    break;
                case 4:
                    // Delete a patient
                    deletePatient();
                    break;
                case 5:
                    // Go back to main menu
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void addPatient() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter patient name: ");
        String name = sc.nextLine();
        System.out.print("Enter patient age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume the leftover newline character
        System.out.print("Enter patient gender: ");
        String gender = sc.nextLine();
        System.out.print("Enter patient disease: ");
        String disease = sc.nextLine();
        System.out.print("Enter patient room number: ");
        int roomNumber = sc.nextInt();
        System.out.print("Enter admit date (YYYY-MM-DD): ");
        String admissionDate = sc.next();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO Patients (name, age, gender, disease, room_number, admission_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, disease);
            stmt.setInt(5, roomNumber);
            stmt.setDate(6, Date.valueOf(admissionDate)); // Convert string to Date
            stmt.executeUpdate();
            System.out.println("Patient added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }

    public static void viewPatients() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM Patients";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Print table headers
            System.out.printf("%-10s %-20s %-5s %-10s %-20s %-15s %-15s%n",
                    "Patient ID", "Name", "Age", "Gender", "Disease", "Room Number", "Admission Date");
            System.out.println("----------------------------------------------------------------------------");

            // Print table rows
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-5d %-10s %-20s %-15d %-15s%n",
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("disease"),
                        rs.getInt("room_number"),
                        rs.getDate("admission_date"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing patients: " + e.getMessage());
        }
    }

    public static void updatePatient() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID to update: ");
        int patientId = sc.nextInt();
        sc.nextLine(); // consume newline character

        // Prompt the user for the new details
        System.out.print("Enter new patient name: ");
        String name = sc.nextLine();
        System.out.print("Enter new patient age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume newline character
        System.out.print("Enter new patient gender: ");
        String gender = sc.nextLine();
        System.out.print("Enter new patient disease: ");
        String disease = sc.nextLine();
        System.out.print("Enter new patient room number: ");
        int roomNumber = sc.nextInt();
        System.out.print("Enter new admit date (YYYY-MM-DD): ");
        String admissionDate = sc.next();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE Patients SET name = ?, age = ?, gender = ?, disease = ?, room_number = ?, admission_date = ? WHERE patient_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, disease);
            stmt.setInt(5, roomNumber);
            stmt.setDate(6, Date.valueOf(admissionDate)); // Convert string to Date
            stmt.setInt(7, patientId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Patient details updated successfully!");
            } else {
                System.out.println("No patient found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }

    public static void deletePatient() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID to delete: ");
        int patientId = sc.nextInt();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM Patients WHERE patient_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, patientId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Patient deleted successfully!");
            } else {
                System.out.println("No patient found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting patient: " + e.getMessage());
        }
    }
}

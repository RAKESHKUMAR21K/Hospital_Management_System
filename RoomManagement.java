import java.sql.*;
import java.util.Scanner;

public class RoomManagement {

    public static void roomMenu() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Room Management ===");
            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Update Room Status");
            System.out.println("4. Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // Add a new room
                    addRoom();
                    break;
                case 2:
                    // View room records
                    viewRooms();
                    break;
                case 3:
                    // Update room status
                    updateRoomStatus();
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

    public static void addRoom() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();
        System.out.print("Enter room type (e.g., General, ICU): ");
        String roomType = sc.next();
        System.out.print("Enter room status (Available/Occupied): ");
        String roomStatus = sc.next();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO Room (room_number, room_type, room_status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, roomNumber);
            stmt.setString(2, roomType);
            stmt.setString(3, roomStatus);
            stmt.executeUpdate();
            System.out.println("Room added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }

    public static void viewRooms() {
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Room")) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No rooms found.");
                return;
            }

            // Print table headers
            System.out.printf("%-15s %-20s %-15s%n",
                    "Room Number", "Room Type", "Room Status");
            System.out.println("------------------------------------------------------");

            // Print table rows
            while (rs.next()) {
                System.out.printf("%-15d %-20s %-15s%n",
                        rs.getInt("room_number"),
                        rs.getString("room_type"),
                        rs.getString("room_status"));
            }

        } catch (SQLException e) {
            System.out.println("Error viewing rooms: " + e.getMessage());
        }
    }

    public static void updateRoomStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter room number to update status: ");
        int roomNumber = sc.nextInt();
        System.out.print("Enter new room status (Available/Occupied): ");
        String roomStatus = sc.next();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE Room SET room_status = ? WHERE room_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, roomStatus);
            stmt.setInt(2, roomNumber);
            stmt.executeUpdate();
            System.out.println("Room status updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating room status: " + e.getMessage());
        }
    }
}

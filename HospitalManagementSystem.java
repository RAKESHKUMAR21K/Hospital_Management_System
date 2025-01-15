import java.util.*;

public class HospitalManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Admin login process
        System.out.print("Enter username: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = sc.next();

        if (AdminLogin.login(username, password)) {
            System.out.println("Login successful!");

            // Display Main Menu after successful login
            boolean exit = false;
            while (!exit) {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Manage Patients");
                System.out.println("2. Manage Doctors");
                System.out.println("3. Manage Rooms");
                System.out.println("4. Manage Services");
                System.out.println("5. Manage Appointments");
                System.out.println("6. Manage Billing");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // Patient Management
                        PatientManagement.patientMenu();
                        break;
                    case 2:
                        // Doctor Management
                        DoctorManagement.doctorMenu();
                        break;
                    case 3:
                        // Room Management
                        RoomManagement.roomMenu();
                        break;
                    case 4:
                        // Service Management
                        ServiceManagement.serviceMenu();
                        break;
                    case 5:
                        // Appointment Management
                        AppointmentManagement.appointmentMenu();
                        break;
                    case 6:
                        // Billing Management
                        BillingManagement.billingMenu();
                        break;
                    case 7:
                        // Exit system
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}

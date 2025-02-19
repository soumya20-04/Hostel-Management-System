import java.util.Scanner;  // Importing Scanner class for input handling
import java.util.InputMismatchException;  // Importing InputMismatchException for handling invalid input
import java.util.ArrayList;  // Importing ArrayList class for dynamic array management

// Define the HostelManagementSystem class
class HostelManagementSystem {

    // Define the Student class to represent each student
    static class Student {
        int id;  // Student's ID
        String name;  // Student's Name
        double feePaid;  // Fee paid by the student
        Room allocatedRoom;  // Room allocated to the student

        // Constructor to initialize Student details
        Student(int id, String name, double feePaid) {
            this.id = id;
            this.name = name;
            this.feePaid=feePaid;
            this.allocatedRoom = null;  // Initially no room allocated
        }

        // Method to display student's information
        void displayStudentInfo() {
            System.out.println("The details of the Registered Student are:");
            System.out.println("ID: " + id + ", Name: " + name + ", Fee Paid: ₹" + feePaid);
            if (allocatedRoom != null) {
                System.out.println("Allocated Room: " + allocatedRoom.roomNumber);
            } else {
                System.out.println("No room allocated.");
            }
        }
    }

    // Define the Room class to represent each room
    static class Room {
        String roomNumber;  // Room number
        boolean isAvailable;  // Availability of the room
        int capacity;  // Maximum capacity of the room
        int currentOccupants;  // Current number of occupants in the room

        // Constructor to initialize Room details
        Room(String roomNumber, boolean isAvailable, int capacity) {
            this.roomNumber = roomNumber;
            this.isAvailable = isAvailable;
            this.capacity = capacity;
            this.currentOccupants = 0;  // Initially, the room has no occupants
        }

        // Method to allocate room to a student if possible
        String allocateRoom() {
            if (isAvailable && currentOccupants < capacity) {
                currentOccupants++;  // Increment current occupants
                if (currentOccupants == capacity) {
                    isAvailable = false;  // Mark room as unavailable if it is at full capacity
                }
                return "Room " + roomNumber + " allocated successfully!";
            }
            return "Room " + roomNumber + " is either not available or already at full capacity!";
        }
    }

    // ArrayList to store the list of registered students
    static ArrayList<Student> students = new ArrayList<>();
    // Array of Room objects representing the hostel rooms
    static Room[] rooms = {
            new Room("101", true, 2),  // Room 101, capacity of 2
            new Room("102", true, 2),  // Room 102, capacity of 2
            new Room("103", true, 1),  // Room 103, capacity of 1
            new Room("104", true, 3),  // Room 104, capacity of 3
            new Room("105", true, 1)   // Room 105, capacity of 1
    };

    // Create a Scanner object for reading user input
    static Scanner scanner = new Scanner(System.in);

    // Main method, the entry point of the program
    public static void main(String[] args) {
        int choice;

        // Infinite loop for the menu until the user exits
        while (true) {
            try {
                System.out.println("\nWelcome!");
                System.out.println("\nHostel Management System");
                System.out.println("1. Register a Student");
                System.out.println("2. Allocate Room");
                System.out.println("3. View Student Details");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();  // Take user choice as input
                scanner.nextLine();  // Consume newline character

                // Switch statement to handle menu options
                switch (choice) {
                    case 1:
                        registerStudent();  // Call method to register a student
                        break;
                    case 2:
                        allocateRoom();  // Call method to allocate room to a student
                        break;
                    case 3:
                        viewStudentDetails();  // Call method to view student details
                        break;
                    case 4:
                        System.out.println("Exiting the system...");  // Exit the system
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice! Try again.");  // Handle invalid choice
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();  // Clear the buffer to prevent infinite loop
            }
        }
    }

    // Method to register a new student
    public static void registerStudent() {
        int id = -1;  // Default invalid value for ID
        double feePaid = -1.0;  // Default invalid value for feePaid
        String name = "";  // Default empty name

        try {
            // Validate Student ID (ensure it is a positive integer)
            while (id <= 0) {
                System.out.print("Enter Student ID : ");
                if (scanner.hasNextInt()) {
                    id = scanner.nextInt();  // Take student ID as input
                    scanner.nextLine();  // Consume newline
                    if (id <= 0) {
                        System.out.println("Invalid ID! Please enter a positive integer.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid integer for ID.");
                    scanner.nextLine();  // Clear invalid input
                }
            }

            // Validate Student Name (ensure it contains only alphabets, no spaces)
            while (true) {
                System.out.print("Enter Student Name: ");
                name = scanner.nextLine();
                if (name.matches("[a-zA-Z]+")) {  // Regex to allow only alphabets
                    break;  // Exit loop if name is valid
                } else {
                    System.out.println("Invalid name! Please enter a name with only alphabetic characters and no spaces.");
                }
            }

            // Validate Fee Paid (ensure fee is greater than 0)
            while (feePaid <= 0) {
                System.out.print("Enter Fee Paid (₹): ₹");
                if (scanner.hasNextDouble()) {
                    feePaid = scanner.nextDouble();  // Take fee as input
                    scanner.nextLine();  // Consume newline
                    if (feePaid <= 0) {
                        System.out.println("Fee paid cannot be negative. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid number for fee paid.");
                    scanner.nextLine();  // Clear invalid input
                }
            }

            // Create a new student object and add to the students list
            Student newStudent = new Student(id, name, feePaid);
            students.add(newStudent);  // Add the student to the list
            System.out.println("Student Registered Successfully!");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
    // Method to allocate a room to a student
    public static void allocateRoom() {
    int id = -1;  // Default invalid value
    try {
        // Validate Student ID (positive integer)
        while (id <= 0) {
            System.out.print("Enter Student ID to Allocate Room: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                if (id <= 0) {
                    System.out.println("Invalid ID! Please enter a valid positive integer.");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid integer for the student ID.");
                scanner.nextLine();  // Clear the invalid input
            }
        }

        Student student = findStudent(id);
        if (student != null) {
            // If the student already has a room, notify
            if (student.allocatedRoom != null) {
                System.out.println("This student already has a room allocated: " + student.allocatedRoom.roomNumber);
                return;
            }

            boolean roomAllocated = false;
            for (Room room : rooms) {
                if (room.isAvailable && room.currentOccupants < room.capacity) {
                    String message = room.allocateRoom();
                    student.allocatedRoom = room;  // Assign room to the student
                    System.out.println("Room allocated to student: " + student.name);
                    System.out.println(message);
                    roomAllocated = true;
                    break;
                }
            }
            if (!roomAllocated) {
                System.out.println("No rooms available at the moment.");
            }
        } else {
            System.out.println("Student not found.");
        }
    } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter a valid student ID.");
        scanner.nextLine();  // Clear the buffer
    }
}
   // Method to view student details
public static void viewStudentDetails() {
    int id = -1;  // Default invalid value
    while (id <= 0) {  // Loop until a valid ID is entered
        try {
            System.out.print("Enter Student ID to View Details: ");
            id = scanner.nextInt();  // Take student ID as input
            scanner.nextLine();  // Consume newline

            if (id <= 0) {
                System.out.println("Invalid ID! Please enter a valid positive integer.");
            } else {
                Student student = findStudent(id);  // Find the student by ID
                if (student != null) {
                    student.displayStudentInfo();  // Display the student's information
                    break;  // Exit the loop if student is found
                } else {
                    System.out.println("Student not found. Please re-enter a valid Student ID.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid student ID.");
            scanner.nextLine();  // Clear the buffer
        }
    }
}


    // Helper method to find a student by their ID
    public static Student findStudent(int id) {
        for (Student student : students) {
            if (student.id == id) {  // Match the student ID
                return student;  // Return the student object if found
            }
        }
        return null;  // Return null if student is not found
    }
}


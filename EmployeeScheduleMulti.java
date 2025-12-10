import java.util.Scanner; // Import scanner package
import java.time.LocalDate;


public class EmployeeScheduleMulti {
    Scanner scanner = new Scanner(System.in);
    Schedule schedule = new Schedule(); // Make schedule accessible
    ScheduleMap scheduleMap = new ScheduleMap(); // ChatGPT-assisted TreeMap version

    void chooseFunction() {
        while (true) { // Switch for each option the user can select
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a day");
            System.out.println("2. Remove a day");
            System.out.println("3. Update day");
            System.out.println("4. Display schedule");
            System.out.println("5. Exit");
            System.out.println("6. Search for an employee");
            System.out.println("7. Display schedule (TreeMap version)");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addDayInput();
                    break;
                case "2":
                    removeDayInput();
                    break;
                case "3":
                    updateDayInput();
                    break;
                case "4":
                    schedule.displaySchedule();
                    break;
                case "5":
                    System.out.println("Exiting program...");
                    return;
                case "6":
                    searchEmployeeInput();
                    break;
                case "7":
                    scheduleMap.display();
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    
    void addDayInput() {
        System.out.print("Enter date (yyyy-MM-dd): ");
        LocalDate date = null;
    
        while (date == null) {
            date = validateDateInput(scanner.nextLine());
        }
    
        System.out.print("Enter employees (comma-separated): ");
        String employees = null;
    
        while (employees == null) {
            employees = validateEmployeeList(scanner.nextLine());
        }
    
        // Convert LocalDate to String to match Schedule.addDay method
        schedule.addDay(date.toString(), employees);
    }
    
    

    void removeDayInput() { // Removes a day from the schedule
        System.out.print("Enter the date you wish to remove (yyyy-mm-dd): ");
        String date = scanner.nextLine();
        schedule.removeDay(date);
    }

    void updateDayInput() {
        System.out.print("\n Enter the date that you wish to udpate: ");
        String date = scanner.nextLine();
        System.out.print("\n Enter the updated employees working that day: ");
        String updatedEmployees = scanner.nextLine();
        schedule.updateDay(date, updatedEmployees);
    }

    // Added with assistance from ChatGPT:
    // Allows user to search for any employee and see all dates they are scheduled.
    void searchEmployeeInput() {
        System.out.print("Enter the employee name to search for: ");
        String name = scanner.nextLine();
        schedule.searchEmployee(name);
    }

    // Added with assistance from ChatGPT:
    // Validates user input for date format (yyyy-MM-dd) and ensures it is a real calendar date.
    LocalDate validateDateInput(String input) {
        try {
            return LocalDate.parse(input);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter date as yyyy-MM-dd.");
            return null;
        }
    }

    // Added with assistance from ChatGPT:
    // Ensures the user enters at least one employee and cleans whitespace.
    String validateEmployeeList(String input) {
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            System.out.println("Employee list cannot be empty.");
            return null;
        }

        // Remove accidental spaces after commas
        trimmed = trimmed.replaceAll("\\s*,\\s*", ", ");

        return trimmed;
    }


    public static void main(String[] args) { // Main method to test interactive input functionality and schedule 
        EmployeeScheduleMulti app = new EmployeeScheduleMulti();

        // Add some sample data
        app.schedule.addDay("2025-10-06", "Jacob, Daniel");
        app.schedule.addDay("2025-10-07", "Keller");
        app.schedule.addDay("2025-10-08", "Matthew, Jacob");

        System.out.println("=== INITIAL SCHEDULE ===");
        app.schedule.displaySchedule();

        // Start interactive input
        app.chooseFunction();
    }
}

class DayNode { // Node class to construct a day in the schedule
    String date;
    String employees;
    DayNode next;

    DayNode(String date, String employees) {
        this.date = date;
        this.employees = employees;
        this.next = null;
    }
}

class Schedule { // Employee schedule class
    DayNode head;

    // Added with assistance from ChatGPT:
    // This method checks whether a date already exists in the schedule
    // to prevent inserting duplicate entries.
    boolean containsDate(String date) {
        DayNode current = head;
        while (current != null) {
            if (current.date.equals(date)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    void addDay(String date, String employees) {

        // Added with assistance from ChatGPT:
        // Prevent adding a duplicate date to maintain schedule integrity
        if (containsDate(date)) {
            System.out.println("Error: A schedule entry for " + date + " already exists.");
            return;
        }    
        DayNode newNode = new DayNode(date, employees);
        LocalDate newDate = LocalDate.parse(date);
        // Case 1: List is empty or new date is earlier than the head
        if (head == null || LocalDate.parse(head.date).isAfter(newDate)) {
            newNode.next = head;
            head = newNode;   
            return;
        }

        // Case 2: Find the correct insertion point
        DayNode current = head;
        while (current.next != null && !LocalDate.parse(current.next.date).isAfter(newDate)) {
            current = current.next;
        }
        
        //Insert new node
        newNode.next = current.next;
        current.next = newNode;
    }

    void removeDay(String date) { // Removes a day from the schedule
        if (head == null) {
            System.out.println("\nSchedule is empty. Cannot remove.");
            return;
        }
        if (head.date.equals(date)) {
            head = head.next;
            System.out.println("\nRemoved " + date);
            return;
        }
        DayNode current = head;
        while (current.next != null && !current.next.date.equals(date)) {
            current = current.next;
        }
        if (current.next == null) {
            System.out.println("Date not found: " + date);
        } else {
            current.next = current.next.next;
            System.out.println("Removed " + date);
        }
    }

    void updateDay(String date, String newEmployees) {
        DayNode current = head;
        
        while (current != null) {
            if (current.date.equals(date)) {
                current.employees = newEmployees;
                return;
            }
            current = current.next;
        }
        System.out.println("\nDate value not found. Check formatting. ");
    }

    void displaySchedule() { // Displays the whole employee schedule with dates and employees working
        if (head == null) {
            System.out.println("\nSchedule is empty.");
            return;
        }
        System.out.println("\nEmployee Schedule:");
        DayNode current = head;
        while (current != null) {
            System.out.println(current.date + ": " + current.employees);
            current = current.next;
        }
    }

    // Added with assistance from ChatGPT:
    // This method searches the entire schedule for any dates where
    // the specified employee is working.
    void searchEmployee(String employeeName) {
        boolean found = false;
        DayNode current = head;

        while (current != null) {
            // Case-insensitive search inside employee lists
            if (current.employees.toLowerCase().contains(employeeName.toLowerCase())) {
                System.out.println(employeeName + " works on: " + current.date);
                found = true;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("No schedule entries found for: " + employeeName);
        }
    }

    

}
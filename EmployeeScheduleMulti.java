import java.util.Scanner; // Import scanner package

public class EmployeeScheduleMulti {
    Scanner scanner = new Scanner(System.in);
    Schedule schedule = new Schedule(); // Make schedule accessible

    void chooseFunction() {
        while (true) { // Switch for each option the user can select
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a day");
            System.out.println("2. Remove a day");
            System.out.println("3. Update day");
            System.out.println("4. Display schedule");
            System.out.println("5. Exit");
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
                case "5":
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    void addDayInput() { // Adds a day to the linkedlist with the date and employee names
        System.out.print("Please enter the date you wish to add (yyyy-mm-dd): ");
        String date = scanner.nextLine();
        System.out.print("Please enter the employees (separated by commas): ");
        String employees = scanner.nextLine();
        schedule.addDay(date, employees);
        System.out.println("Added " + date + " with employees: " + employees);
    }

    void removeDayInput() { // Removes a day from the schedule
        System.out.print("Enter the date you wish to remove (yyyy-mm-dd): ");
        String date = scanner.nextLine();
        schedule.removeDay(date);
    }

    void updateDayInput() {
        System.out.print("Enter the date that you wish to udpate: ");
        String date = scanner.nextLine();
        System.out.print("Enter the updated employees working that day: ");
        String updatedEmployees = scanner.nextLine();
        schedule.updateDay(date, updatedEmployees);
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

    void addDay(String date, String employees) {
        DayNode newNode = new DayNode(date, employees);
        if (head == null) {
            head = newNode;
            return;
        }
        DayNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    void removeDay(String date) { // Removes a day from the schedule
        if (head == null) {
            System.out.println("Schedule is empty. Cannot remove.");
            return;
        }
        if (head.date.equals(date)) {
            head = head.next;
            System.out.println("Removed " + date);
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
            if (current.date == date) {
                current.employees = newEmployees;
                return;
            }
            current = current.next;
        }
        System.out.println("Date value not found. Check formatting. ");
    }

    void displaySchedule() { // Displays the whole employee schedule with dates and employees working
        if (head == null) {
            System.out.println("Schedule is empty.");
            return;
        }
        System.out.println("Employee Schedule:");
        DayNode current = head;
        while (current != null) {
            System.out.println(current.date + ": " + current.employees);
            current = current.next;
        }
    }
}

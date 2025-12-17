
/*
 * ScheduleMap
 * -----------
 * TreeMap-based implementation of an employee scheduling system.
 *
 * This class is an alternative to the linked-list Schedule implementation
 * and is intended for comparison and extra credit purposes in the Capstone project.
 *
 * Key Characteristics:
 * - Uses TreeMap to automatically keep dates sorted
 * - Prevents duplicate dates by design
 * - Supports add, remove, update, display, and search operations
 * - Designed to integrate easily with a GUI
 *
 * NOTE:
 * - Existing inline comments have been preserved exactly as written.
 * - Only BLOCK COMMENTS have been added for documentation.
 * - No logic, structure, or behavior has been changed.
 */

// TreeMap-based rewrite of the Schedule class
// Added with assistance from ChatGPT for Capstone extra credit and comparison.
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/*
 * ScheduleMap class
 * -----------------
 * Stores scheduled employees by date using a TreeMap.
 * The TreeMap ensures natural ordering by LocalDate.
 */
public class ScheduleMap {

    /*
     * Internal data structure:
     * - Key   : LocalDate (schedule date)
     * - Value : String containing comma-separated employee names
     */
    private TreeMap<LocalDate, String> map = new TreeMap<>();

    /*
     * Adds a new day to the schedule
     * - Automatically prevents duplicate dates
     *
     * @param date       the date to add
     * @param employees  comma-separated employee names
     * @return true if added successfully, false if date already exists
     */
    // Prevents duplicates automatically
    public boolean addDay(LocalDate date, String employees) {
        if (map.containsKey(date)) {
            System.out.println("A schedule already exists for this date.");
            return false;
        }
        map.put(date, employees);
        return true;
    }

    /*
     * Removes a scheduled day
     *
     * @param date the date to remove
     * @return true if removed, false if date not found
     */
    public boolean removeDay(LocalDate date) {
        if (!map.containsKey(date)) {
            System.out.println("Date not found.");
            return false;
        }
        map.remove(date);
        return true;
    }

    /*
     * Updates the employees scheduled for an existing date
     *
     * @param date       the date to update
     * @param employees  new comma-separated employee list
     * @return true if updated, false if date not found
     */
    public boolean updateDay(LocalDate date, String employees) {
        if (!map.containsKey(date)) {
            System.out.println("Date not found.");
            return false;
        }
        map.put(date, employees);
        return true;
    }

    /*
     * Displays the entire schedule in chronological order
     */
    public void display() {
        if (map.isEmpty()) {
            System.out.println("Schedule is empty.");
            return;
        }
        for (Map.Entry<LocalDate, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /*
     * Searches the schedule for an employee name
     * Prints all dates where the employee appears
     */
    // Added with ChatGPT assistance:
    // Returns all dates where an employee is scheduled
    public void searchEmployee(String name) {
        boolean found = false;
        for (Map.Entry<LocalDate, String> entry : map.entrySet()) {
            if (entry.getValue().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Employee not found in schedule.");
        }
    }

    /*
     * Getter for GUI or external access
     *
     * @return the underlying TreeMap storing the schedule
     */
    // ChatGPT-assisted getter for GUI access
    public TreeMap<LocalDate, String> getMap() {
        return map;
    }
}
